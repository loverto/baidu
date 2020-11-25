package org.ylf.web.rest;

import org.ylf.domain.AddressLibraryCoordinate;
import org.ylf.service.AddressLibraryCoordinateService;
import org.ylf.web.rest.errors.BadRequestAlertException;
import org.ylf.service.dto.AddressLibraryCoordinateCriteria;
import org.ylf.service.AddressLibraryCoordinateQueryService;

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
 * REST controller for managing {@link org.ylf.domain.AddressLibraryCoordinate}.
 */
@RestController
@RequestMapping("/api")
public class AddressLibraryCoordinateResource {

    private final Logger log = LoggerFactory.getLogger(AddressLibraryCoordinateResource.class);

    private static final String ENTITY_NAME = "addressLibraryCoordinate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressLibraryCoordinateService addressLibraryCoordinateService;

    private final AddressLibraryCoordinateQueryService addressLibraryCoordinateQueryService;

    public AddressLibraryCoordinateResource(AddressLibraryCoordinateService addressLibraryCoordinateService, AddressLibraryCoordinateQueryService addressLibraryCoordinateQueryService) {
        this.addressLibraryCoordinateService = addressLibraryCoordinateService;
        this.addressLibraryCoordinateQueryService = addressLibraryCoordinateQueryService;
    }

    /**
     * {@code POST  /address-library-coordinates} : Create a new addressLibraryCoordinate.
     *
     * @param addressLibraryCoordinate the addressLibraryCoordinate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressLibraryCoordinate, or with status {@code 400 (Bad Request)} if the addressLibraryCoordinate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/address-library-coordinates")
    public ResponseEntity<AddressLibraryCoordinate> createAddressLibraryCoordinate(@RequestBody AddressLibraryCoordinate addressLibraryCoordinate) throws URISyntaxException {
        log.debug("REST request to save AddressLibraryCoordinate : {}", addressLibraryCoordinate);
        if (addressLibraryCoordinate.getId() != null) {
            throw new BadRequestAlertException("A new addressLibraryCoordinate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressLibraryCoordinate result = addressLibraryCoordinateService.save(addressLibraryCoordinate);
        return ResponseEntity.created(new URI("/api/address-library-coordinates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /address-library-coordinates} : Updates an existing addressLibraryCoordinate.
     *
     * @param addressLibraryCoordinate the addressLibraryCoordinate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressLibraryCoordinate,
     * or with status {@code 400 (Bad Request)} if the addressLibraryCoordinate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressLibraryCoordinate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/address-library-coordinates")
    public ResponseEntity<AddressLibraryCoordinate> updateAddressLibraryCoordinate(@RequestBody AddressLibraryCoordinate addressLibraryCoordinate) throws URISyntaxException {
        log.debug("REST request to update AddressLibraryCoordinate : {}", addressLibraryCoordinate);
        if (addressLibraryCoordinate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressLibraryCoordinate result = addressLibraryCoordinateService.save(addressLibraryCoordinate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressLibraryCoordinate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /address-library-coordinates} : get all the addressLibraryCoordinates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addressLibraryCoordinates in body.
     */
    @GetMapping("/address-library-coordinates")
    public ResponseEntity<List<AddressLibraryCoordinate>> getAllAddressLibraryCoordinates(AddressLibraryCoordinateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AddressLibraryCoordinates by criteria: {}", criteria);
        Page<AddressLibraryCoordinate> page = addressLibraryCoordinateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /address-library-coordinates/count} : count all the addressLibraryCoordinates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/address-library-coordinates/count")
    public ResponseEntity<Long> countAddressLibraryCoordinates(AddressLibraryCoordinateCriteria criteria) {
        log.debug("REST request to count AddressLibraryCoordinates by criteria: {}", criteria);
        return ResponseEntity.ok().body(addressLibraryCoordinateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /address-library-coordinates/:id} : get the "id" addressLibraryCoordinate.
     *
     * @param id the id of the addressLibraryCoordinate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressLibraryCoordinate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/address-library-coordinates/{id}")
    public ResponseEntity<AddressLibraryCoordinate> getAddressLibraryCoordinate(@PathVariable Long id) {
        log.debug("REST request to get AddressLibraryCoordinate : {}", id);
        Optional<AddressLibraryCoordinate> addressLibraryCoordinate = addressLibraryCoordinateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressLibraryCoordinate);
    }

    /**
     * {@code DELETE  /address-library-coordinates/:id} : delete the "id" addressLibraryCoordinate.
     *
     * @param id the id of the addressLibraryCoordinate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/address-library-coordinates/{id}")
    public ResponseEntity<Void> deleteAddressLibraryCoordinate(@PathVariable Long id) {
        log.debug("REST request to delete AddressLibraryCoordinate : {}", id);
        addressLibraryCoordinateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
