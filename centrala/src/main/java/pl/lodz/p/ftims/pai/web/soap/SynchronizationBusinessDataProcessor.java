package pl.lodz.p.ftims.pai.web.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.lodz.p.ftims.pai.domain.*;
import pl.lodz.p.ftims.pai.repository.*;

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
        for(Department d : request.getDepartment()) {
            d.setSourceDepartmentId(departmentId);
            d.setDataSourceId(d.getId());
        }
        for(Transporter t : request.getTransporter()){
            t.setSourceDepartmentId(departmentId);
            t.setDataSourceId(t.getId());
        }
        for(Employee e : request.getEmployee()){
            e.setSourceDepartmentId(departmentId);
            e.setDataSourceId(e.getId());
        }
        for(Transit t : request.getTransit()){
            t.setSourceDepartmentId(departmentId);
            t.setDataSourceId(t.getId());
        }

        List<DoubleKey> newDepartmentKeys = request.getDepartment().stream().map(DoubleKeyed::key).collect(Collectors.toList());
        List<DoubleKey> newTransporterKeys = request.getTransporter().stream().map(DoubleKeyed::key).collect(Collectors.toList());
        List<DoubleKey> newEmployeeKeys = request.getEmployee().stream().map(DoubleKeyed::key).collect(Collectors.toList());
        List<DoubleKey> newTransitKeys = request.getTransit().stream().map(DoubleKeyed::key).collect(Collectors.toList());

        List<Object[]> oldDepartmentKeysObj = departmentRepository.selectDoubleKeys();
        List<Object[]> oldTransporterKeysObj = transporterRepository.selectDoubleKeys();
        List<Object[]> oldEmployeeKeysObj = employeeRepository.selectDoubleKeys();
        List<Object[]> oldTransitKeysObj = transitRepository.selectDoubleKeys();

        DoubleKeyJpaConverter converter = new DoubleKeyJpaConverter();
        List<DoubleKey> oldDepartmentKeys = oldDepartmentKeysObj.stream().map(converter::convertToEntityAttribute).collect(Collectors.toList());
        List<DoubleKey> oldTransporterKeys = oldTransporterKeysObj.stream().map(converter::convertToEntityAttribute).collect(Collectors.toList());
        List<DoubleKey> oldEmployeeKeys = oldEmployeeKeysObj.stream().map(converter::convertToEntityAttribute).collect(Collectors.toList());
        List<DoubleKey> oldTransitKeys = oldTransitKeysObj.stream().map(converter::convertToEntityAttribute).collect(Collectors.toList());

        oldDepartmentKeys.stream()
            .filter(old -> !newDepartmentKeys.stream().anyMatch($ -> oldKeyMatchesNew(old, $)))
            .forEach(old -> departmentRepository.delete(old.id));

        oldTransporterKeys.stream()
            .filter(old -> !newTransporterKeys.stream().anyMatch($ -> oldKeyMatchesNew(old, $)))
            .forEach(old -> transporterRepository.delete(old.id));

        oldEmployeeKeys.stream()
            .filter(old -> !newEmployeeKeys.stream().anyMatch($ -> oldKeyMatchesNew(old, $)))
            .forEach(old -> employeeRepository.delete(old.id));

        oldTransitKeys.stream()
            .filter(old -> !newTransitKeys.stream().anyMatch($ -> oldKeyMatchesNew(old, $)))
            .forEach(old -> transitRepository.delete(old.id));

    }

    private boolean oldKeyMatchesNew(DoubleKey oldKey, DoubleKey newKey) {
        return newKey.dataSourceId.equals(oldKey.dataSourceId)
            && newKey.sourceDepartmentId.equals(oldKey.sourceDepartmentId);
    }

    private void updateOrSaveNew(SynchronizationBusinessDataRequest request){
        final Long departmentId = request.getDepartmentId();
        for(Department d : request.getDepartment()) {
            d.setSourceDepartmentId(departmentId);
            d.setDataSourceId(d.getId());
        }
        for(Transporter t : request.getTransporter()){
            t.setSourceDepartmentId(departmentId);
            t.setDataSourceId(t.getId());
        }
        for(Employee e : request.getEmployee()){
            e.setSourceDepartmentId(departmentId);
            e.setDataSourceId(e.getId());
        }
        for(Transit t : request.getTransit()){
            t.setSourceDepartmentId(departmentId);
            t.setDataSourceId(t.getId());
        }
        departmentRepository.save(request.getDepartment());
        transporterRepository.save(request.getTransporter());
        employeeRepository.save(request.getEmployee());
        transitRepository.save(request.getTransit());
    }
}
