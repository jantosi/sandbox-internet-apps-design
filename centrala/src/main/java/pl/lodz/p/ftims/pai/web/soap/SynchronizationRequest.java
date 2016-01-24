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

    protected long department;

    public long getDepartment() {
        return department;
    }

    public void setDepartment(long value) {
        this.department = value;
    }

}
