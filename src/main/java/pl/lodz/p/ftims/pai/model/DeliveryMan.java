package pl.lodz.p.ftims.pai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import java.util.Arrays;

/**
 * @author alisowsk
 */
@Entity
public class DeliveryMan {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    private String email;

    protected DeliveryMan(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
