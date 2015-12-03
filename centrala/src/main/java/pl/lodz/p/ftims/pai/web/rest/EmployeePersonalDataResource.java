package pl.lodz.p.ftims.pai.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.lodz.p.ftims.pai.domain.EmployeePersonalData;
import pl.lodz.p.ftims.pai.repository.EmployeePersonalDataRepository;
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
 * REST controller for managing EmployeePersonalData.
 */
@RestController
@RequestMapping("/api")
public class EmployeePersonalDataResource {

    private final Logger log = LoggerFactory.getLogger(EmployeePersonalDataResource.class);

    @Inject
    private EmployeePersonalDataRepository employeePersonalDataRepository;

    /**
     * POST  /employeePersonalDatas -> Create a new employeePersonalData.
     */
    @RequestMapping(value = "/employeePersonalDatas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeePersonalData> createEmployeePersonalData(@RequestBody EmployeePersonalData employeePersonalData) throws URISyntaxException {
        log.debug("REST request to save EmployeePersonalData : {}", employeePersonalData);
        if (employeePersonalData.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeePersonalData cannot already have an ID").body(null);
        }
        EmployeePersonalData result = employeePersonalDataRepository.save(employeePersonalData);
        return ResponseEntity.created(new URI("/api/employeePersonalDatas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeePersonalData", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeePersonalDatas -> Updates an existing employeePersonalData.
     */
    @RequestMapping(value = "/employeePersonalDatas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeePersonalData> updateEmployeePersonalData(@RequestBody EmployeePersonalData employeePersonalData) throws URISyntaxException {
        log.debug("REST request to update EmployeePersonalData : {}", employeePersonalData);
        if (employeePersonalData.getId() == null) {
            return createEmployeePersonalData(employeePersonalData);
        }
        EmployeePersonalData result = employeePersonalDataRepository.save(employeePersonalData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeePersonalData", employeePersonalData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeePersonalDatas -> get all the employeePersonalDatas.
     */
    @RequestMapping(value = "/employeePersonalDatas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeePersonalData> getAllEmployeePersonalDatas() {
        log.debug("REST request to get all EmployeePersonalDatas");
        return employeePersonalDataRepository.findAll();
    }

    /**
     * GET  /employeePersonalDatas/:id -> get the "id" employeePersonalData.
     */
    @RequestMapping(value = "/employeePersonalDatas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeePersonalData> getEmployeePersonalData(@PathVariable Long id) {
        log.debug("REST request to get EmployeePersonalData : {}", id);
        return Optional.ofNullable(employeePersonalDataRepository.findOne(id))
            .map(employeePersonalData -> new ResponseEntity<>(
                employeePersonalData,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeePersonalDatas/:id -> delete the "id" employeePersonalData.
     */
    @RequestMapping(value = "/employeePersonalDatas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeePersonalData(@PathVariable Long id) {
        log.debug("REST request to delete EmployeePersonalData : {}", id);
        employeePersonalDataRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeePersonalData", id.toString())).build();
    }
}
