package pl.lodz.p.ftims.pai.web.rest;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by antosikj (Jakub Antosik) on 21/01/16.
 */
public class DbSynchronizationResponse {
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
