package pl.lodz.p.ftims.pai.web.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.lodz.p.ftims.pai.domain.*;
import pl.lodz.p.ftims.pai.repository.DepartmentRepository;
import pl.lodz.p.ftims.pai.repository.EmployeeRepository;
import pl.lodz.p.ftims.pai.repository.TransitRepository;
import pl.lodz.p.ftims.pai.repository.TransporterRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class SynchronizationBusinessDataProcessor {
    private static final String NAMESPACE_URI = "http://osemka.com";

    @Autowired
    private TransporterRepository transporterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TransitRepository transitRepository;

    public SynchronizationBusinessDataProcessor() {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "synchronizationBusinessDataRequest")
    @ResponsePayload
    public SynchronizationBusinessDataResponse synchronize(@RequestPayload SynchronizationBusinessDataRequest request) {
        SynchronizationBusinessDataResponse response = new SynchronizationBusinessDataResponse();
        response.setStartDate(Calendar.getInstance().getTime());
        try{
            deleteNotExisting(request);
            updateOrSaveNew(request);
            response.setStatus("200");
        } catch (Exception ex){
            response.setStatus("500");
        }

        return response;
    }

    private void deleteNotExisting(SynchronizationBusinessDataRequest request) {
        final Long departmentId = request.getDepartmentId();

        List<DoubleKey> newDepartmentKeys = request.getDepartment().stream().map(DoubleKeyed::key).collect(Collectors.toList());
        List<DoubleKey> newTransporterKeys = request.getTransporter().stream().map(DoubleKeyed::key).collect(Collectors.toList());
        List<DoubleKey> newEmployeeKeys = request.getEmployee().stream().map(DoubleKeyed::key).collect(Collectors.toList());
        List<DoubleKey> newTransitKeys = request.getTransit().stream().map(DoubleKeyed::key).collect(Collectors.toList());

        List<DoubleKey> oldDepartmentKeys = departmentRepository.selectDoubleKeys();
        List<DoubleKey> oldTransporterKeys = transporterRepository.selectDoubleKeys();
        List<DoubleKey> oldEmployeeKeys = employeeRepository.selectDoubleKeys();
        List<DoubleKey> oldTransitKeys = transitRepository.selectDoubleKeys();

        oldDepartmentKeys.stream().filter($ -> !newDepartmentKeys.contains($)).forEach($ -> departmentRepository.delete($.id));
        oldTransporterKeys.stream().filter($ -> !newTransporterKeys.contains($)).forEach($ -> transporterRepository.delete($.id));
        oldEmployeeKeys.stream().filter($ -> !newEmployeeKeys.contains($)).forEach($ -> employeeRepository.delete($.id));
        oldTransitKeys.stream().filter($ -> !newTransitKeys.contains($)).forEach($ -> transitRepository.delete($.id));

    }

    private void updateOrSaveNew(SynchronizationBusinessDataRequest request){
        final Long departmentId = request.getDepartmentId();
        for(Department d : request.getDepartment()) {
            d.setDataSourceId(departmentId);
            d.setDataSourceId(d.getId());
        }
        for(Transporter t : request.getTransporter()){
            t.setDataSourceId(departmentId);
            t.setDataSourceId(t.getId());
        }
        for(Employee e : request.getEmployee()){
            e.setDataSourceId(departmentId);
            e.setDataSourceId(e.getId());
        }
        for(Transit t : request.getTransit()){
            t.setDataSourceId(departmentId);
            t.setDataSourceId(t.getId());
        }
        departmentRepository.save(request.getDepartment());
        transporterRepository.save(request.getTransporter());
        employeeRepository.save(request.getEmployee());
        transitRepository.save(request.getTransit());
    }
}
