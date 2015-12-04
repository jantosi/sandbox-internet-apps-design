package pl.lodz.p.ftims.pai.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.lodz.p.ftims.pai.domain.Transporter;
import pl.lodz.p.ftims.pai.repository.TransporterRepository;
import pl.lodz.p.ftims.pai.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Transporter.
 */
@RestController
@RequestMapping("/api")
public class TransporterResource {

    private final Logger log = LoggerFactory.getLogger(TransporterResource.class);

    @Inject
    private TransporterRepository transporterRepository;

    /**
     * POST  /transporters -> Create a new transporter.
     */
    @RequestMapping(value = "/transporters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transporter> createTransporter(@RequestBody Transporter transporter) throws URISyntaxException {
        log.debug("REST request to save Transporter : {}", transporter);
        if (transporter.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new transporter cannot already have an ID").body(null);
        }
        Transporter result = transporterRepository.save(transporter);
        return ResponseEntity.created(new URI("/api/transporters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("transporter", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transporters -> Updates an existing transporter.
     */
    @RequestMapping(value = "/transporters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transporter> updateTransporter(@RequestBody Transporter transporter) throws URISyntaxException {
        log.debug("REST request to update Transporter : {}", transporter);
        if (transporter.getId() == null) {
            return createTransporter(transporter);
        }
        Transporter result = transporterRepository.save(transporter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("transporter", transporter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transporters -> get all the transporters.
     */
    @RequestMapping(value = "/transporters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Transporter> getAllTransporters() {
        log.debug("REST request to get all Transporters");
        return transporterRepository.findAll();
    }

    /**
     * GET  /transporters/:id -> get the "id" transporter.
     */
    @RequestMapping(value = "/transporters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transporter> getTransporter(@PathVariable Long id) {
        log.debug("REST request to get Transporter : {}", id);
        return Optional.ofNullable(transporterRepository.findOne(id))
            .map(transporter -> new ResponseEntity<>(
                transporter,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /transporters/:id -> delete the "id" transporter.
     */
    @RequestMapping(value = "/transporters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTransporter(@PathVariable Long id) {
        log.debug("REST request to delete Transporter : {}", id);
        transporterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("transporter", id.toString())).build();
    }
}
