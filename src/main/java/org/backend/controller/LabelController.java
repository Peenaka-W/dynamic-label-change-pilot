package org.backend.controller;

import jakarta.validation.Valid;
import org.backend.dto.LabelDto;
import org.backend.dto.LabelPersonalizationFullRequest;
import org.backend.dto.LabelPersonalizationRequest;
import org.backend.dto.LabelResponse;
import org.backend.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for label operations.
 */
@RestController
@RequestMapping("/fusionX")
public class LabelController {

    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    /**
     * Get all labels for a module, tenant, and screen.
     *
     * @param moduleName the name of the module
     * @param tenantId the ID of the tenant
     * @param screenName the name of the screen
     * @return ResponseEntity with the label response
     */
    @GetMapping("/{moduleName}/{tenantId}/{screenName}/labels")
    public ResponseEntity<LabelResponse> getLabels(
            @PathVariable String moduleName,
            @PathVariable String tenantId,
            @PathVariable String screenName) {

        LabelResponse response = labelService.getLabels(moduleName, tenantId, screenName);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a specific label by its key.
     *
     * @param moduleName the name of the module
     * @param tenantId the ID of the tenant
     * @param screenName the name of the screen
     * @param labelKey the key of the label
     * @return ResponseEntity with the label DTO
     */
    @GetMapping("/{moduleName}/{tenantId}/{screenName}/labels/{labelKey}")
    public ResponseEntity<LabelDto> getLabel(
            @PathVariable String moduleName,
            @PathVariable String tenantId,
            @PathVariable String screenName,
            @PathVariable String labelKey) {

        LabelDto labelDto = labelService.getLabel(moduleName, tenantId, screenName, labelKey);
        return ResponseEntity.ok(labelDto);
    }

    /**
     * Create or update a label personalization.
     *
     * @param moduleName the name of the module
     * @param tenantId the ID of the tenant
     * @param screenName the name of the screen
     * @param labelKey the key of the label
     * @param request the label personalization request
     * @return ResponseEntity with the updated label response
     */
    @PostMapping("/{moduleName}/{tenantId}/{screenName}/labels/{labelKey}")
    public ResponseEntity<LabelResponse> createOrUpdateLabelPersonalization(
            @PathVariable String moduleName,
            @PathVariable String tenantId,
            @PathVariable String screenName,
            @PathVariable String labelKey,
            @RequestBody LabelPersonalizationRequest request) {

        LabelDto updatedLabel = labelService.updateLabelPersonalization(
                moduleName,
                tenantId,
                screenName,
                labelKey,
                request.getPersonalizedName(),
                request.getUpdatedBy());

        // Use the single LabelDto constructor
        LabelResponse response = new LabelResponse(moduleName, screenName, tenantId, updatedLabel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * Update a label personalization.
     *
     * @param moduleName the name of the module
     * @param tenantId the ID of the tenant
     * @param screenName the name of the screen
     * @param labelKey the key of the label
     * @param request the label personalization request
     * @return ResponseEntity with the updated label response
     */
    @PutMapping("/{moduleName}/{tenantId}/{screenName}/labels/{labelKey}")
    public ResponseEntity<LabelResponse> updateLabelPersonalization(
            @PathVariable String moduleName,
            @PathVariable String tenantId,
            @PathVariable String screenName,
            @PathVariable String labelKey,
            @RequestBody LabelPersonalizationRequest request) {

        LabelDto updatedLabel = labelService.updateLabelPersonalization(
                moduleName,
                tenantId,
                screenName,
                labelKey,
                request.getPersonalizedName(),
                request.getUpdatedBy());

        // Use the single LabelDto constructor
        LabelResponse response = new LabelResponse(moduleName, screenName, tenantId, updatedLabel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/labels/personalize")
//    public ResponseEntity<LabelResponse> updateLabelPersonalization(
//            @RequestBody LabelPersonalizationFullRequest request) {
//
//        LabelDto updatedLabel = labelService.updateLabelPersonalization(
//                request.getModuleName(),
//                request.getTenantId(),
//                request.getScreenName(),
//                request.getLabelKey(),
//                request.getPersonalizedName(),
//                request.getUpdatedBy());
//
//        LabelResponse response = new LabelResponse(
//                request.getModuleName(),
//                request.getScreenName(),
//                request.getTenantId(),
//                updatedLabel
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    //---- For list of labels ----
    @PostMapping("/labels/personalize")
    public ResponseEntity<LabelResponse> updateLabelPersonalizations(
            @RequestBody List<LabelPersonalizationFullRequest> requests) {
        LabelResponse response = labelService.updateLabelPersonalizations(requests);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

