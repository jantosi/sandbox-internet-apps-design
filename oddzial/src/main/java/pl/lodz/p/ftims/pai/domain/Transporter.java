package pl.lodz.p.ftims.pai.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Transporter.
 */
@Entity
@Table(name = "transporter")
public class Transporter implements Serializable {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            '}';
    }
}
