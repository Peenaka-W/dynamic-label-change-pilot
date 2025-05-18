
package org.backend.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Response DTO for label operations, supporting both collections of labels and single label responses.
 */
public class LabelResponse implements Serializable {

    private boolean success;
    private String message;
    private String moduleName;
    private String screenName;
    private String tenantId;
    private List<LabelDto> labels;

    // Constructors
    public LabelResponse() {
    }

    // Constructor for collection of labels (original use case)
    public LabelResponse(String moduleName, String screenName, String tenantId, List<LabelDto> labels) {
        this.success = true;
        this.message = "Labels retrieved successfully";
        this.moduleName = moduleName;
        this.screenName = screenName;
        this.tenantId = tenantId;
        this.labels = labels != null ? labels : Collections.emptyList();
    }

    // Constructor for single label (personalization use case)
    public LabelResponse(String moduleName, String screenName, String tenantId, LabelDto label) {
        this.success = true;
        this.message = "Label personalized successfully";
        this.moduleName = moduleName;
        this.screenName = screenName;
        this.tenantId = tenantId;
        this.labels = label != null ? Collections.singletonList(label) : Collections.emptyList();
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<LabelDto> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelDto> labels) {
        this.labels = labels != null ? labels : Collections.emptyList();
    }

    // Helper method to set a single label
    public void setLabel(LabelDto label) {
        this.labels = label != null ? Collections.singletonList(label) : Collections.emptyList();
    }
}





//package org.backend.dto;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * Response DTO for returning a collection of labels.
// */
//public class LabelResponse implements Serializable {
//
//    private String moduleName;
//    private String screenName;
//    private String tenantId;
//    private List<LabelDto> labels;
//
//    // Constructors
//    public LabelResponse() {
//    }
//
//    public LabelResponse(String moduleName, String screenName, String tenantId, List<LabelDto> labels) {
//        this.moduleName = moduleName;
//        this.screenName = screenName;
//        this.tenantId = tenantId;
//        this.labels = labels;
//    }
//
//    // Getters and Setters
//    public String getModuleName() {
//        return moduleName;
//    }
//
//    public void setModuleName(String moduleName) {
//        this.moduleName = moduleName;
//    }
//
//    public String getScreenName() {
//        return screenName;
//    }
//
//    public void setScreenName(String screenName) {
//        this.screenName = screenName;
//    }
//
//    public String getTenantId() {
//        return tenantId;
//    }
//
//    public void setTenantId(String tenantId) {
//        this.tenantId = tenantId;
//    }
//
//    public List<LabelDto> getLabels() {
//        return labels;
//    }
//
//    public void setLabels(List<LabelDto> labels) {
//        this.labels = labels;
//    }
//}