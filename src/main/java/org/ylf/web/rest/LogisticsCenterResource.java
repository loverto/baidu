package org.ylf.web.rest;

import org.ylf.domain.LogisticsCenter;
import org.ylf.service.LogisticsCenterService;
import org.ylf.web.rest.errors.BadRequestAlertException;
import org.ylf.service.dto.LogisticsCenterCriteria;
import org.ylf.service.LogisticsCenterQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.ylf.domain.LogisticsCenter}.
 */
@RestController
@RequestMapping("/api")
public class LogisticsCenterResource {

    private final Logger log = LoggerFactory.getLogger(LogisticsCenterResource.class);

    private static final String ENTITY_NAME = "logisticsCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogisticsCenterService logisticsCenterService;

    private final LogisticsCenterQueryService logisticsCenterQueryService;

    public LogisticsCenterResource(LogisticsCenterService logisticsCenterService, LogisticsCenterQueryService logisticsCenterQueryService) {
        this.logisticsCenterService = logisticsCenterService;
        this.logisticsCenterQueryService = logisticsCenterQueryService;
    }

    /**
     * {@code POST  /logistics-centers} : Create a new logisticsCenter.
     *
     * @param logisticsCenter the logisticsCenter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logisticsCenter, or with status {@code 400 (Bad Request)} if the logisticsCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/logistics-centers")
    public ResponseEntity<LogisticsCenter> createLogisticsCenter(@RequestBody LogisticsCenter logisticsCenter) throws URISyntaxException {
        log.debug("REST request to save LogisticsCenter : {}", logisticsCenter);
        if (logisticsCenter.getId() != null) {
            throw new BadRequestAlertException("A new logisticsCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogisticsCenter result = logisticsCenterService.save(logisticsCenter);
        return ResponseEntity.created(new URI("/api/logistics-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /logistics-centers} : Updates an existing logisticsCenter.
     *
     * @param logisticsCenter the logisticsCenter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logisticsCenter,
     * or with status {@code 400 (Bad Request)} if the logisticsCenter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logisticsCenter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/logistics-centers")
    public ResponseEntity<LogisticsCenter> updateLogisticsCenter(@RequestBody LogisticsCenter logisticsCenter) throws URISyntaxException {
        log.debug("REST request to update LogisticsCenter : {}", logisticsCenter);
        if (logisticsCenter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogisticsCenter result = logisticsCenterService.save(logisticsCenter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logisticsCenter.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /logistics-centers} : get all the logisticsCenters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logisticsCenters in body.
     */
    @GetMapping("/logistics-centers")
    public ResponseEntity<List<LogisticsCenter>> getAllLogisticsCenters(LogisticsCenterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LogisticsCenters by criteria: {}", criteria);
        Page<LogisticsCenter> page = logisticsCenterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /logistics-centers/count} : count all the logisticsCenters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/logistics-centers/count")
    public ResponseEntity<Long> countLogisticsCenters(LogisticsCenterCriteria criteria) {
        log.debug("REST request to count LogisticsCenters by criteria: {}", criteria);
        return ResponseEntity.ok().body(logisticsCenterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /logistics-centers/:id} : get the "id" logisticsCenter.
     *
     * @param id the id of the logisticsCenter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logisticsCenter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/logistics-centers/{id}")
    public ResponseEntity<LogisticsCenter> getLogisticsCenter(@PathVariable Long id) {
        log.debug("REST request to get LogisticsCenter : {}", id);
        Optional<LogisticsCenter> logisticsCenter = logisticsCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logisticsCenter);
    }

    /**
     * {@code DELETE  /logistics-centers/:id} : delete the "id" logisticsCenter.
     *
     * @param id the id of the logisticsCenter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/logistics-centers/{id}")
    public ResponseEntity<Void> deleteLogisticsCenter(@PathVariable Long id) {
        log.debug("REST request to delete LogisticsCenter : {}", id);
        logisticsCenterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
