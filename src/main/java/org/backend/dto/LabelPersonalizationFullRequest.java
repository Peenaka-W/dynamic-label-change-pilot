package org.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import java.io.Serializable;

/**
 * Request DTO for updating a label personalization with all parameters.
 */
@Getter
public class LabelPersonalizationFullRequest implements Serializable {

    @NotBlank(message = "Module name cannot be blank")
    private String moduleName;

    @NotBlank(message = "Tenant ID cannot be blank")
    private String tenantId;

    @NotBlank(message = "Screen name cannot be blank")
    private String screenName;

    @NotBlank(message = "Label key cannot be blank")
    private String labelKey;

    @NotBlank(message = "Personalized name cannot be blank")
    private String personalizedName;

    private String updatedBy;

    public @NotBlank(message = "Module name cannot be blank") String getModuleName() {
        return moduleName;
    }

    public void setModuleName(@NotBlank(message = "Module name cannot be blank") String moduleName) {
        this.moduleName = moduleName;
    }

    public @NotBlank(message = "Tenant ID cannot be blank") String getTenantId() {
        return tenantId;
    }

    public void setTenantId(@NotBlank(message = "Tenant ID cannot be blank") String tenantId) {
        this.tenantId = tenantId;
    }

    public @NotBlank(message = "Screen name cannot be blank") String getScreenName() {
        return screenName;
    }

    public void setScreenName(@NotBlank(message = "Screen name cannot be blank") String screenName) {
        this.screenName = screenName;
    }

    public @NotBlank(message = "Label key cannot be blank") String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(@NotBlank(message = "Label key cannot be blank") String labelKey) {
        this.labelKey = labelKey;
    }

    public @NotBlank(message = "Personalized name cannot be blank") String getPersonalizedName() {
        return personalizedName;
    }

    public void setPersonalizedName(@NotBlank(message = "Personalized name cannot be blank") String personalizedName) {
        this.personalizedName = personalizedName;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}