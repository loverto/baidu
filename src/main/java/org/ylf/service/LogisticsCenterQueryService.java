package org.ylf.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.ylf.domain.LogisticsCenter;
import org.ylf.domain.*; // for static metamodels
import org.ylf.repository.LogisticsCenterRepository;
import org.ylf.service.dto.LogisticsCenterCriteria;

/**
 * Service for executing complex queries for {@link LogisticsCenter} entities in the database.
 * The main input is a {@link LogisticsCenterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LogisticsCenter} or a {@link Page} of {@link LogisticsCenter} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LogisticsCenterQueryService extends QueryService<LogisticsCenter> {

    private final Logger log = LoggerFactory.getLogger(LogisticsCenterQueryService.class);

    private final LogisticsCenterRepository logisticsCenterRepository;

    public LogisticsCenterQueryService(LogisticsCenterRepository logisticsCenterRepository) {
        this.logisticsCenterRepository = logisticsCenterRepository;
    }

    /**
     * Return a {@link List} of {@link LogisticsCenter} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LogisticsCenter> findByCriteria(LogisticsCenterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LogisticsCenter> specification = createSpecification(criteria);
        return logisticsCenterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LogisticsCenter} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LogisticsCenter> findByCriteria(LogisticsCenterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LogisticsCenter> specification = createSpecification(criteria);
        return logisticsCenterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LogisticsCenterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LogisticsCenter> specification = createSpecification(criteria);
        return logisticsCenterRepository.count(specification);
    }

    /**
     * Function to convert {@link LogisticsCenterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LogisticsCenter> createSpecification(LogisticsCenterCriteria criteria) {
        Specification<LogisticsCenter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LogisticsCenter_.id));
            }
            if (criteria.getRegionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegionName(), LogisticsCenter_.regionName));
            }
            if (criteria.getLogisticsCenterName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogisticsCenterName(), LogisticsCenter_.logisticsCenterName));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitude(), LogisticsCenter_.longitude));
            }
            if (criteria.getDimension() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDimension(), LogisticsCenter_.dimension));
            }
            if (criteria.getCreationBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationBy(), LogisticsCenter_.creationBy));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), LogisticsCenter_.creationDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), LogisticsCenter_.lastModifiedBy));
            }
            if (criteria.getLastModifyTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifyTime(), LogisticsCenter_.lastModifyTime));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), LogisticsCenter_.lastModifiedDate));
            }
            if (criteria.getAvailable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailable(), LogisticsCenter_.available));
            }
        }
        return specification;
    }
}
