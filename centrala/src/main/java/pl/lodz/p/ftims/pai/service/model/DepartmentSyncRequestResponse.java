package pl.lodz.p.ftims.pai.service.model;

import java.util.Date;

/**
 * Created by antosikj (Jakub Antosik) on 05/02/16.
 */
public class DepartmentSyncRequestResponse {
    String status;
    Date startDate;

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
