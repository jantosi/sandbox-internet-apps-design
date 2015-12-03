package pl.lodz.p.ftims.pai.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.lodz.p.ftims.pai.domain.TransporterInfo;
import pl.lodz.p.ftims.pai.repository.TransporterInfoRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TransporterInfo.
 */
@RestController
@RequestMapping("/api")
public class TransporterInfoResource {

    private final Logger log = LoggerFactory.getLogger(TransporterInfoResource.class);

    @Inject
    private TransporterInfoRepository transporterInfoRepository;

    /**
     * POST  /transporterInfos -> Create a new transporterInfo.
     */
    @RequestMapping(value = "/transporterInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TransporterInfo> createTransporterInfo(@RequestBody TransporterInfo transporterInfo) throws URISyntaxException {
        log.debug("REST request to save TransporterInfo : {}", transporterInfo);
        if (transporterInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new transporterInfo cannot already have an ID").body(null);
        }
        TransporterInfo result = transporterInfoRepository.save(transporterInfo);
        return ResponseEntity.created(new URI("/api/transporterInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("transporterInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transporterInfos -> Updates an existing transporterInfo.
     */
    @RequestMapping(value = "/transporterInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TransporterInfo> updateTransporterInfo(@RequestBody TransporterInfo transporterInfo) throws URISyntaxException {
        log.debug("REST request to update TransporterInfo : {}", transporterInfo);
        if (transporterInfo.getId() == null) {
            return createTransporterInfo(transporterInfo);
        }
        TransporterInfo result = transporterInfoRepository.save(transporterInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("transporterInfo", transporterInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transporterInfos -> get all the transporterInfos.
     */
    @RequestMapping(value = "/transporterInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TransporterInfo>> getAllTransporterInfos(Pageable pageable)
        throws URISyntaxException {
        Page<TransporterInfo> page = transporterInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transporterInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transporterInfos/:id -> get the "id" transporterInfo.
     */
    @RequestMapping(value = "/transporterInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TransporterInfo> getTransporterInfo(@PathVariable Long id) {
        log.debug("REST request to get TransporterInfo : {}", id);
        return Optional.ofNullable(transporterInfoRepository.findOne(id))
            .map(transporterInfo -> new ResponseEntity<>(
                transporterInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /transporterInfos/:id -> delete the "id" transporterInfo.
     */
    @RequestMapping(value = "/transporterInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTransporterInfo(@PathVariable Long id) {
        log.debug("REST request to delete TransporterInfo : {}", id);
        transporterInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("transporterInfo", id.toString())).build();
    }
}
