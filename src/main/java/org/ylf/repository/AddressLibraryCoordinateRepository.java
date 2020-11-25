package org.ylf.repository;

import org.ylf.domain.AddressLibraryCoordinate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AddressLibraryCoordinate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressLibraryCoordinateRepository extends JpaRepository<AddressLibraryCoordinate, Long>, JpaSpecificationExecutor<AddressLibraryCoordinate> {
}
