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

import org.ylf.domain.AddressLibraryCoordinate;
import org.ylf.domain.*; // for static metamodels
import org.ylf.repository.AddressLibraryCoordinateRepository;
import org.ylf.service.dto.AddressLibraryCoordinateCriteria;

/**
 * Service for executing complex queries for {@link AddressLibraryCoordinate} entities in the database.
 * The main input is a {@link AddressLibraryCoordinateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AddressLibraryCoordinate} or a {@link Page} of {@link AddressLibraryCoordinate} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressLibraryCoordinateQueryService extends QueryService<AddressLibraryCoordinate> {

    private final Logger log = LoggerFactory.getLogger(AddressLibraryCoordinateQueryService.class);

    private final AddressLibraryCoordinateRepository addressLibraryCoordinateRepository;

    public AddressLibraryCoordinateQueryService(AddressLibraryCoordinateRepository addressLibraryCoordinateRepository) {
        this.addressLibraryCoordinateRepository = addressLibraryCoordinateRepository;
    }

    /**
     * Return a {@link List} of {@link AddressLibraryCoordinate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AddressLibraryCoordinate> findByCriteria(AddressLibraryCoordinateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AddressLibraryCoordinate> specification = createSpecification(criteria);
        return addressLibraryCoordinateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AddressLibraryCoordinate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressLibraryCoordinate> findByCriteria(AddressLibraryCoordinateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AddressLibraryCoordinate> specification = createSpecification(criteria);
        return addressLibraryCoordinateRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressLibraryCoordinateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AddressLibraryCoordinate> specification = createSpecification(criteria);
        return addressLibraryCoordinateRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressLibraryCoordinateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AddressLibraryCoordinate> createSpecification(AddressLibraryCoordinateCriteria criteria) {
        Specification<AddressLibraryCoordinate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AddressLibraryCoordinate_.id));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressId(), AddressLibraryCoordinate_.addressId));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaId(), AddressLibraryCoordinate_.areaId));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), AddressLibraryCoordinate_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AddressLibraryCoordinate_.name));
            }
            if (criteria.getZipCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZipCode(), AddressLibraryCoordinate_.zipCode));
            }
            if (criteria.getParentCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParentCode(), AddressLibraryCoordinate_.parentCode));
            }
            if (criteria.getAddrLevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddrLevel(), AddressLibraryCoordinate_.addrLevel));
            }
            if (criteria.getAvailable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailable(), AddressLibraryCoordinate_.available));
            }
            if (criteria.getSeqNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeqNo(), AddressLibraryCoordinate_.seqNo));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), AddressLibraryCoordinate_.createTime));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), AddressLibraryCoordinate_.updateTime));
            }
            if (criteria.getLimitLine() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLimitLine(), AddressLibraryCoordinate_.limitLine));
            }
            if (criteria.getPinyinPrefix() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPinyinPrefix(), AddressLibraryCoordinate_.pinyinPrefix));
            }
            if (criteria.getDistrictLatitudeLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrictLatitudeLongitude(), AddressLibraryCoordinate_.districtLatitudeLongitude));
            }
        }
        return specification;
    }
}
