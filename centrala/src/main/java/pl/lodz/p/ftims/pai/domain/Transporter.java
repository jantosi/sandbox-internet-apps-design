package pl.lodz.p.ftims.pai.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Transporter.
 */
@Entity
@Table(name = "transporter")
public class Transporter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "purchase_time")
    private ZonedDateTime purchaseTime;

    @Column(name = "withdrawal_time")
    private ZonedDateTime withdrawalTime;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Department location;

    @OneToOne    private TransporterInfo transporterInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(ZonedDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public ZonedDateTime getWithdrawalTime() {
        return withdrawalTime;
    }

    public void setWithdrawalTime(ZonedDateTime withdrawalTime) {
        this.withdrawalTime = withdrawalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getLocation() {
        return location;
    }

    public void setLocation(Department department) {
        this.location = department;
    }

    public TransporterInfo getTransporterInfo() {
        return transporterInfo;
    }

    public void setTransporterInfo(TransporterInfo transporterInfo) {
        this.transporterInfo = transporterInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transporter transporter = (Transporter) o;
        return Objects.equals(id, transporter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transporter{" +
            "id=" + id +
            ", purchaseTime='" + purchaseTime + "'" +
            ", withdrawalTime='" + withdrawalTime + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
