package pl.lodz.p.ftims.pai.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Transporter.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transporter", propOrder = {
    "id",
})
@Entity
@Table(name = "transporter")
public class Transporter implements Serializable {

    @XmlElement(required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @XmlTransient
    @Column(name = "purchase_time")
    private ZonedDateTime purchaseTime;

    @XmlTransient
    @Column(name = "withdrawal_time")
    private ZonedDateTime withdrawalTime;

    @XmlTransient
    @Column(name = "name")
    private String name;

    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Department location;

    @XmlTransient
    @OneToOne
    private TransporterInfo transporterInfo;

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
