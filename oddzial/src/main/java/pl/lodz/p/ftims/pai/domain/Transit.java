package pl.lodz.p.ftims.pai.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pl.lodz.p.ftims.pai.domain.enumeration.TransitType;

/**
 * A Transit.
 */
@Entity
@Table(name = "transit")
public class Transit implements Serializable {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransitType type;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter transporter;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
