package org.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

/**
 * Request DTO for updating a label personalization.
 */

public class LabelPersonalizationRequest implements Serializable {

    @NotBlank(message = "Personalized name cannot be blank")
    private String personalizedName;

    private String updatedBy;

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







//package org.backend.dto;
//
//import jakarta.validation.constraints.NotBlank;
//import lombok.Getter;
//
//import java.io.Serializable;
//
///**
// * Request DTO for updating a label personalization.
// */
//@Getter
//public class LabelPersonalizationRequest implements Serializable {
//
//    // Getters and Setters
//    @NotBlank(message = "Personalized name cannot be blank")
//    private String personalizedName;
//
//    private String updatedBy;
//
//    // Constructors
//    public LabelPersonalizationRequest() {
//    }
//
//    public LabelPersonalizationRequest(String personalizedName, String updatedBy) {
//        this.personalizedName = personalizedName;
//        this.updatedBy = updatedBy;
//    }
//
//    public void setPersonalizedName(String personalizedName) {
//        this.personalizedName = personalizedName;
//    }
//
//    public void setUpdatedBy(String updatedBy) {
//        this.updatedBy = updatedBy;
//    }
//}