package org.backend.repository;

import org.backend.entity.Label;
import org.backend.entity.LabelPersonalization;
import org.backend.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for LabelPersonalization entity operations.
 */
@Repository
public interface LabelPersonalizationRepository extends JpaRepository<LabelPersonalization, Long> {

    /**
     * Find personalization by label and tenant.
     *
     * @param label the label
     * @param tenant the tenant
     * @return an Optional containing the personalization if found
     */
    Optional<LabelPersonalization> findByLabelAndTenant(Label label, Tenant tenant);

    /**
     * Find all personalizations for a tenant and a list of labels.
     *
     * @param tenant the tenant
     * @param labels list of labels
     * @return list of personalizations
     */
    List<LabelPersonalization> findByTenantAndLabelIn(Tenant tenant, List<Label> labels);

    /**
     * Find all personalizations for a specific tenant and labels associated with a screen.
     *
     * @param tenantId the tenant ID
     * @param screenId the screen ID
     * @return list of personalizations
     */
    @Query("SELECT lp FROM LabelPersonalization lp " +
            "JOIN lp.label l " +
            "JOIN l.screen s " +
            "JOIN lp.tenant t " +
            "WHERE t.tenantId = :tenantId AND s.id = :screenId")
    List<LabelPersonalization> findByTenantIdAndScreenId(String tenantId, Long screenId);
}