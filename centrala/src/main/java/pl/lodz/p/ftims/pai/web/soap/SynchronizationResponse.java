package pl.lodz.p.ftims.pai.web.soap;

import pl.lodz.p.ftims.pai.domain.Department;
import pl.lodz.p.ftims.pai.domain.Employee;
import pl.lodz.p.ftims.pai.domain.Transporter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "transporter",
    "employee",
    "department"
})
@XmlRootElement(name = "synchronizationResponse")
public class SynchronizationResponse {

    @XmlElement(required = true)
    protected List<Transporter> transporter;

    @XmlElement(required = true)
    protected List<Employee> employee;

    @XmlElement(required = true)
    protected List<Department> department;

    public List<Transporter> getTransporter() {
        if (transporter == null) {
            transporter = new ArrayList<>();
        }
        return this.transporter;
    }

    public List<Employee> getEmployee() {
        if(employee == null){
            employee = new ArrayList<>();
        }
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public List<Department> getDepartment() {
        if(department == null){
            department = new ArrayList<>();
        }
        return department;
    }

    public void setDepartment(List<Department> department) {
        this.department = department;
    }

    public void setTransporter(List<Transporter> transporter) {
        this.transporter = transporter;
    }
}
