package pl.lodz.p.ftims.pai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pl.lodz.p.ftims.pai.domain.enumeration.TransporterType;

/**
 * A Transporter.
 */
@Entity
@Table(name = "transporter")
public class Transporter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransporterType type;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @OneToMany(mappedBy = "transporter")
    @JsonIgnore
    private Set<Transit> transits = new HashSet<>();

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

    public Set<Transit> getTransits() {
        return transits;
    }

    public void setTransits(Set<Transit> transits) {
        this.transits = transits;
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
            ", type='" + type + "'" +
            ", model='" + model + "'" +
            '}';
    }
}
