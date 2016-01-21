package pl.lodz.p.ftims.pai.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.ftims.pai.service.DbSynchronizationService;

import java.net.URISyntaxException;
import java.util.Calendar;

/**
 * Created by antosikj (Jakub Antosik) on 21/01/16.
 */

@RestController
@RequestMapping("/api")
public class DbSynchronizationResource {

    @Autowired
    private DbSynchronizationService dbSynchronizationService;

    @RequestMapping(value="/dbsync/headquarters", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DbSynchronizationResponse> startDatabaseSyncHq() throws URISyntaxException{

        try {

            dbSynchronizationService.startDatabaseSyncWithHeadquarters();

            DbSynchronizationResponse response = new DbSynchronizationResponse();
            response.setStartDate(Calendar.getInstance().getTime());
            response.setStatus("started");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}