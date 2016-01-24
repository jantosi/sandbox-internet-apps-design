package pl.lodz.p.ftims.pai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.ftims.pai.domain.Department;
import pl.lodz.p.ftims.pai.domain.Employee;
import pl.lodz.p.ftims.pai.domain.Transporter;
import pl.lodz.p.ftims.pai.repository.DepartmentRepository;
import pl.lodz.p.ftims.pai.repository.EmployeeRepository;
import pl.lodz.p.ftims.pai.repository.TransitRepository;
import pl.lodz.p.ftims.pai.repository.TransporterRepository;
import pl.lodz.p.ftims.pai.web.soap.SynchronizationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SynchronizationProcessor {

    @Autowired
    private TransporterRepository transporterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TransitRepository transitRepository;

    public SynchronizationProcessor() {
    }

    public void synchronize(SynchronizationResponse response) throws Exception {
        deleteNotExisting(response);
        updateOrSaveNew(response);
    }

    private void deleteNotExisting(SynchronizationResponse response) {
        final List<Long> newDepartmentIds = response.getDepartment().stream().map(Department::getId).collect(Collectors.toList());
        final List<Long> newTransporterIds = response.getTransporter().stream().map(Transporter::getId).collect(Collectors.toList());
        final List<Long> newEployeeIds = response.getEmployee().stream().map(Employee::getId).collect(Collectors.toList());
//        final List<Long> transitIds = response.getDepartment().stream().map(Department::getId).collect(Collectors.toList());

        final List<Long> oldDepartmentIds = departmentRepository.selectIds();
        final List<Long> oldTransporterIds = transporterRepository.selectIds();
        final List<Long> oldEmployeeIds = employeeRepository.selectIds();
//        final List<Long> oldTransitIds = transitRepository.selectIds();

        oldDepartmentIds.stream().filter(oldId -> !newDepartmentIds.contains(oldId)).forEach(departmentRepository::delete);
        oldTransporterIds.stream().filter(oldId -> !newTransporterIds.contains(oldId)).forEach(transporterRepository::delete);
        oldEmployeeIds.stream().filter(oldId -> !newEployeeIds.contains(oldId)).forEach(employeeRepository::delete);
//        oldTransitIds.stream().filter(oldId -> !transitIds.contains(oldId)).forEach(transitRepository::delete);
    }

    private void updateOrSaveNew(SynchronizationResponse response){
        departmentRepository.save(response.getDepartment());
        transporterRepository.save(response.getTransporter());
        employeeRepository.save(response.getEmployee());
//        transitRepository.save(response.getTransit());
    }
}
