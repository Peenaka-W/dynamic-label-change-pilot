package org.backend.repository;


import org.backend.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Tenant entity operations.
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    /**
     * Find a tenant by its tenant ID.
     *
     * @param tenantId the tenant ID
     * @return an Optional containing the tenant if found
     */
    Optional<Tenant> findByTenantId(String tenantId);

    /**
     * Check if a tenant exists by its tenant ID.
     *
     * @param tenantId the tenant ID
     * @return true if the tenant exists, false otherwise
     */
    boolean existsByTenantId(String tenantId);
}