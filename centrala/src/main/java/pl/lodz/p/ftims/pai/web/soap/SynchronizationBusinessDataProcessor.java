package pl.lodz.p.ftims.pai.web.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.lodz.p.ftims.pai.domain.Department;
import pl.lodz.p.ftims.pai.domain.Employee;
import pl.lodz.p.ftims.pai.domain.Transit;
import pl.lodz.p.ftims.pai.domain.Transporter;
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
        final List<Long> newDepartmentIds = request.getDepartment().stream().map(Department::getId).collect(Collectors.toList());
        final List<Long> newTransporterIds = request.getTransporter().stream().map(Transporter::getId).collect(Collectors.toList());
        final List<Long> newEployeeIds = request.getEmployee().stream().map(Employee::getId).collect(Collectors.toList());
        final List<Long> transitIds = request.getTransit().stream().map(Transit::getId).collect(Collectors.toList());

        final List<Long> oldDepartmentIds = departmentRepository.selectIds();
        final List<Long> oldTransporterIds = transporterRepository.selectIds();
        final List<Long> oldEmployeeIds = employeeRepository.selectIds();
        final List<Long> oldTransitIds = transitRepository.selectIds();

//        oldDepartmentIds.stream().filter(oldId -> !newDepartmentIds.contains(oldId)).forEach(departmentRepository::delete);
//        oldTransporterIds.stream().filter(oldId -> !newTransporterIds.contains(oldId)).forEach(transporterRepository::delete);
//        oldEmployeeIds.stream().filter(oldId -> !newEployeeIds.contains(oldId)).forEach(employeeRepository::delete);
//        oldTransitIds.stream().filter(oldId -> !transitIds.contains(oldId)).forEach(transitRepository::delete);
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
