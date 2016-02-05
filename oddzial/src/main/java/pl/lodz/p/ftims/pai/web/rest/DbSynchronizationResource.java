package pl.lodz.p.ftims.pai.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/dbsync")
    public class DbSynchronizationResource {

    @Autowired
    private DbSynchronizationService dbSynchronizationService;

    private final Logger log = LoggerFactory.getLogger(DbSynchronizationResource.class);

    @RequestMapping(value="/businessdata", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DbSynchronizationResponse> startDatabaseSyncBusinessData() throws URISyntaxException{
        try {
            dbSynchronizationService.startDatabaseSyncBusinessData();
            DbSynchronizationResponse response = new DbSynchronizationResponse();
            response.setStartDate(Calendar.getInstance().getTime());
            response.setStatus("started");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @RequestMapping(value="/users", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DbSynchronizationResponse> startDatabaseSyncUsers() throws URISyntaxException{
        log.info("Received user synchronization request.");
        try {
            dbSynchronizationService.startDatabaseSyncUsers();
            DbSynchronizationResponse response = new DbSynchronizationResponse();
            response.setStartDate(Calendar.getInstance().getTime());
            response.setStatus("started");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}
