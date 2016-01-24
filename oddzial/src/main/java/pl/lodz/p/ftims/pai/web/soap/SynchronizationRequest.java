package pl.lodz.p.ftims.pai.web.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "department"
})
@XmlRootElement(name = "synchronizationRequest")
public class SynchronizationRequest {

    protected int department;

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int value) {
        this.department = value;
    }

}
