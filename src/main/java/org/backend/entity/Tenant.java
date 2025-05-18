package org.backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a tenant in the multi-tenant application.
 */
@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, unique = true)
    private String tenantId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active")
    private boolean active = true;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LabelPersonalization> labelPersonalizations = new HashSet<>();

    // Constructors
    public Tenant() {
    }

    public Tenant(String tenantId, String name) {
        this.tenantId = tenantId;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<LabelPersonalization> getLabelPersonalizations() {
        return labelPersonalizations;
    }

    public void setLabelPersonalizations(Set<LabelPersonalization> labelPersonalizations) {
        this.labelPersonalizations = labelPersonalizations;
    }

    // Helper methods to maintain bidirectional relationship
    public void addLabelPersonalization(LabelPersonalization personalization) {
        labelPersonalizations.add(personalization);
        personalization.setTenant(this);
    }

    public void removeLabelPersonalization(LabelPersonalization personalization) {
        labelPersonalizations.remove(personalization);
        personalization.setTenant(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tenant)) return false;
        Tenant tenant = (Tenant) o;
        return id != null && id.equals(tenant.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}