package pl.lodz.p.ftims.pai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import pl.lodz.p.ftims.pai.config.EnvironmentConfiguration;
import pl.lodz.p.ftims.pai.repository.DepartmentRepository;
import pl.lodz.p.ftims.pai.repository.EmployeeRepository;
import pl.lodz.p.ftims.pai.repository.TransitRepository;
import pl.lodz.p.ftims.pai.repository.TransporterRepository;
import pl.lodz.p.ftims.pai.web.soap.SynchronizationBusinessDataRequest;
import pl.lodz.p.ftims.pai.web.soap.SynchronizationBusinessDataResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by <a href="mailto:171131@edu.p.lodz.pl">Andrzej Lisowski</a> on 24.01.16.
 */
@Component
public class SynchronizationBusinessDataSoapService {
    private static final String SERVER_URI = "http://osemka.com";
    private static final String XML_PREFIX = "gs";

    private static final Logger LOG = LoggerFactory.getLogger(SynchronizationBusinessDataSoapService.class);

    @Autowired
    private TransporterRepository transporterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TransitRepository transitRepository;

    @Autowired
    private EnvironmentConfiguration environmentConfiguration;

    public SynchronizationBusinessDataResponse sendSynchronizationRequest() throws Exception {
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        String synchronizationEndpoint = getSynchronizationEndpoint();
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), synchronizationEndpoint);

        soapConnection.close();

        return transformSoapResponseToSynchronizationResponse(soapResponse);
    }

    private SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(XML_PREFIX, SERVER_URI);

        SOAPBody soapBody = envelope.getBody();

        JAXBContext jc = JAXBContext.newInstance(SynchronizationBusinessDataRequest.class);
        SynchronizationBusinessDataRequest request = new SynchronizationBusinessDataRequest();
        request.setEmployee(employeeRepository.findAll());
        request.setDepartment(departmentRepository.findAll());
        request.setTransit(transitRepository.findAll());
        request.setTransporter(transporterRepository.findAll());
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        final Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(request, document);
        soapBody.addDocument(document);

        soapMessage.saveChanges();

        LOG.debug("Request SOAP message: {}", soapMessage);

        return soapMessage;
    }

    private String getSynchronizationEndpoint() {
        return "http://"+environmentConfiguration.getHeadquartersDomain()+":"+ environmentConfiguration.getHeadquartersPort()+ environmentConfiguration.getHeadquartersSynchronizationBusinessDataEndpoint();
    }

    private SynchronizationBusinessDataResponse transformSoapResponseToSynchronizationResponse(SOAPMessage soapResponse) throws Exception {
        Source sourceContent = soapResponse.getSOAPPart().getContent();

        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = xif.createXMLStreamReader(sourceContent);
        xsr.nextTag(); // Advance to Envelope tag
        while (!xsr.getLocalName().equals("synchronizationBusinessDataResponse")) {
            if(xsr.getLocalName().equals("Fault")){
                throw new DbSynchronizationException(xsr.getText());
            }
            xsr.nextTag();
        }

        JAXBContext jc = JAXBContext.newInstance(SynchronizationBusinessDataResponse.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<SynchronizationBusinessDataResponse> je = unmarshaller.unmarshal(xsr, SynchronizationBusinessDataResponse.class);

        return je.getValue();
    }
}
