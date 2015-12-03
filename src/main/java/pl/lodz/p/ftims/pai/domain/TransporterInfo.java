package pl.lodz.p.ftims.pai.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import pl.lodz.p.ftims.pai.domain.enumeration.TransporterType;

/**
 * A TransporterInfo.
 */
@Entity
@Table(name = "transporter_info")
public class TransporterInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransporterType type;

    @Column(name = "model")
    private String model;

    @Column(name = "make")
    private String make;

    @Column(name = "year")
    private ZonedDateTime year;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransporterType getType() {
        return type;
    }

    public void setType(TransporterType type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public ZonedDateTime getYear() {
        return year;
    }

    public void setYear(ZonedDateTime year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransporterInfo transporterInfo = (TransporterInfo) o;
        return Objects.equals(id, transporterInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransporterInfo{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", model='" + model + "'" +
            ", make='" + make + "'" +
            ", year='" + year + "'" +
            '}';
    }
}
