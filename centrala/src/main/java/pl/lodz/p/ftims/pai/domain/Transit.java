package pl.lodz.p.ftims.pai.domain;

import pl.lodz.p.ftims.pai.domain.enumeration.TransitType;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Transit.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transit", propOrder = {
    "id",
    "dataSourceId",
    "sourceDepartmentId",
    "type",
    "startTime",
    "endTime",
    "employee",
    "transporter",
    "departureDepartment"
})
@Entity
@Table(name = "transit")
public class Transit implements Serializable {

    @XmlElement(required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @XmlElement(required = true)
    @Column(name = "dataSourceId")
    private Long dataSourceId;

    @XmlElement(required = true)
    @Column(name = "sourceDepartmentId")
    private Long sourceDepartmentId;

    @XmlElement(required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransitType type;

    @XmlElement(required = true)
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @XmlElement(required = true)
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @XmlElement(required = true)
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @XmlElement(required = true)
    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    @XmlElement(required = true, name = "department")
    @ManyToOne
    @JoinColumn(name = "departure_department_id")
    private Department departureDepartment;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "destination_department_id")
    private Department destinationDepartment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransitType getType() {
        return type;
    }

    public void setType(TransitType type) {
        this.type = type;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Transporter getTransporter() {
        return transporter;
    }

    public void setTransporter(Transporter transporter) {
        this.transporter = transporter;
    }

    public Department getDepartureDepartment() {
        return departureDepartment;
    }

    public void setDepartureDepartment(Department department) {
        this.departureDepartment = department;
    }

    public Department getDestinationDepartment() {
        return destinationDepartment;
    }

    public void setDestinationDepartment(Department department) {
        this.destinationDepartment = department;
    }

    public Long getSourceDepartmentId() {
        return sourceDepartmentId;
    }

    public void setSourceDepartmentId(Long sourceDepartmentId) {
        this.sourceDepartmentId = sourceDepartmentId;
    }

    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transit transit = (Transit) o;
        return Objects.equals(id, transit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transit{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            '}';
    }
}
