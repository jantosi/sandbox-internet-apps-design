package pl.lodz.p.ftims.pai.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.lodz.p.ftims.pai.domain.Transit;
import pl.lodz.p.ftims.pai.repository.TransitRepository;
import pl.lodz.p.ftims.pai.web.rest.util.HeaderUtil;
import pl.lodz.p.ftims.pai.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Transit.
 */
@RestController
@RequestMapping("/api")
public class TransitResource {

    private final Logger log = LoggerFactory.getLogger(TransitResource.class);

    @Inject
    private TransitRepository transitRepository;

    /**
     * POST  /transits -> Create a new transit.
     */
    @RequestMapping(value = "/transits",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transit> createTransit(@Valid @RequestBody Transit transit) throws URISyntaxException {
        log.debug("REST request to save Transit : {}", transit);
        if (transit.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new transit cannot already have an ID").body(null);
        }
        Transit result = transitRepository.save(transit);
        return ResponseEntity.created(new URI("/api/transits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("transit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transits -> Updates an existing transit.
     */
    @RequestMapping(value = "/transits",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transit> updateTransit(@Valid @RequestBody Transit transit) throws URISyntaxException {
        log.debug("REST request to update Transit : {}", transit);
        if (transit.getId() == null) {
            return createTransit(transit);
        }
        Transit result = transitRepository.save(transit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("transit", transit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transits -> get all the transits.
     */
    @RequestMapping(value = "/transits",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Transit>> getAllTransits(Pageable pageable)
        throws URISyntaxException {
        Page<Transit> page = transitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transits/:id -> get the "id" transit.
     */
    @RequestMapping(value = "/transits/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Transit> getTransit(@PathVariable Long id) {
        log.debug("REST request to get Transit : {}", id);
        return Optional.ofNullable(transitRepository.findOne(id))
            .map(transit -> new ResponseEntity<>(
                transit,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /transits/:id -> delete the "id" transit.
     */
    @RequestMapping(value = "/transits/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTransit(@PathVariable Long id) {
        log.debug("REST request to delete Transit : {}", id);
        transitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("transit", id.toString())).build();
    }
}
