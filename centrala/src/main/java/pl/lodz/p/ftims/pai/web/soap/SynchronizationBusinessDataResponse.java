package pl.lodz.p.ftims.pai.web.soap;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "status",
    "startDate",
})
@XmlRootElement(name = "synchronizationBusinessDataResponse")
public class SynchronizationBusinessDataResponse {

    @XmlElement(required = true)
    protected String status;

    @XmlElement(required = true)
    protected Date startDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}

