package org.ylf.service;

import org.ylf.domain.AddressLibraryCoordinate;
import org.ylf.repository.AddressLibraryCoordinateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AddressLibraryCoordinate}.
 */
@Service
@Transactional
public class AddressLibraryCoordinateService {

    private final Logger log = LoggerFactory.getLogger(AddressLibraryCoordinateService.class);

    private final AddressLibraryCoordinateRepository addressLibraryCoordinateRepository;

    public AddressLibraryCoordinateService(AddressLibraryCoordinateRepository addressLibraryCoordinateRepository) {
        this.addressLibraryCoordinateRepository = addressLibraryCoordinateRepository;
    }

    /**
     * Save a addressLibraryCoordinate.
     *
     * @param addressLibraryCoordinate the entity to save.
     * @return the persisted entity.
     */
    public AddressLibraryCoordinate save(AddressLibraryCoordinate addressLibraryCoordinate) {
        log.debug("Request to save AddressLibraryCoordinate : {}", addressLibraryCoordinate);
        return addressLibraryCoordinateRepository.save(addressLibraryCoordinate);
    }

    /**
     * Get all the addressLibraryCoordinates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressLibraryCoordinate> findAll(Pageable pageable) {
        log.debug("Request to get all AddressLibraryCoordinates");
        return addressLibraryCoordinateRepository.findAll(pageable);
    }


    /**
     * Get one addressLibraryCoordinate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AddressLibraryCoordinate> findOne(Long id) {
        log.debug("Request to get AddressLibraryCoordinate : {}", id);
        return addressLibraryCoordinateRepository.findById(id);
    }

    /**
     * Delete the addressLibraryCoordinate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AddressLibraryCoordinate : {}", id);
        addressLibraryCoordinateRepository.deleteById(id);
    }
}
