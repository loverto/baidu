package org.ylf.repository;

import org.ylf.domain.LogisticsCenter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LogisticsCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogisticsCenterRepository extends JpaRepository<LogisticsCenter, Long>, JpaSpecificationExecutor<LogisticsCenter> {
}
