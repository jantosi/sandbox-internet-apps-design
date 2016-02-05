package pl.lodz.p.ftims.pai.domain;


import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Employee.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employee", propOrder = {
    "id",
    "dataSourceId",
    "sourceDepartmentId",
    "login",
})
@Entity
@Table(name = "employee")
public class Employee implements Serializable, DoubleKeyed {

    @XmlElement(required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @XmlElement(required = true)
    @Column(name = "data_source_id")
    private Long dataSourceId;

    @XmlElement(required = true)
    @Column(name = "source_department_id")
    private Long sourceDepartmentId;

    @XmlElement(required = true)
    @Column(name = "login")
    private String login;

    @XmlTransient
    @Column(name = "position")
    private String position;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @XmlTransient
    @OneToOne
    private EmployeePersonalData employeePersonalData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public EmployeePersonalData getEmployeePersonalData() {
        return employeePersonalData;
    }

    public void setEmployeePersonalData(EmployeePersonalData employeePersonalData) {
        this.employeePersonalData = employeePersonalData;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Long getSourceDepartmentId() {
        return sourceDepartmentId;
    }

    public void setSourceDepartmentId(Long sourceDepartmentId) {
        this.sourceDepartmentId = sourceDepartmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", login='" + login + "'" +
            ", position='" + position + "'" +
            '}';
    }
}
