package org.ylf.service;

import org.ylf.domain.LogisticsCenter;
import org.ylf.repository.LogisticsCenterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LogisticsCenter}.
 */
@Service
@Transactional
public class LogisticsCenterService {

    private final Logger log = LoggerFactory.getLogger(LogisticsCenterService.class);

    private final LogisticsCenterRepository logisticsCenterRepository;

    public LogisticsCenterService(LogisticsCenterRepository logisticsCenterRepository) {
        this.logisticsCenterRepository = logisticsCenterRepository;
    }

    /**
     * Save a logisticsCenter.
     *
     * @param logisticsCenter the entity to save.
     * @return the persisted entity.
     */
    public LogisticsCenter save(LogisticsCenter logisticsCenter) {
        log.debug("Request to save LogisticsCenter : {}", logisticsCenter);
        return logisticsCenterRepository.save(logisticsCenter);
    }

    /**
     * Get all the logisticsCenters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LogisticsCenter> findAll(Pageable pageable) {
        log.debug("Request to get all LogisticsCenters");
        return logisticsCenterRepository.findAll(pageable);
    }


    /**
     * Get one logisticsCenter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LogisticsCenter> findOne(Long id) {
        log.debug("Request to get LogisticsCenter : {}", id);
        return logisticsCenterRepository.findById(id);
    }

    /**
     * Delete the logisticsCenter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LogisticsCenter : {}", id);
        logisticsCenterRepository.deleteById(id);
    }
}
