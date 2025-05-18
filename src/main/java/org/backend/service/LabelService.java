package org.backend.service;

import org.backend.dto.LabelDto;
import org.backend.dto.LabelPersonalizationFullRequest;
import org.backend.dto.LabelResponse;
import org.backend.entity.Label;
import org.backend.entity.LabelPersonalization;
import org.backend.entity.Screen;
import org.backend.entity.ScreenModule;
import org.backend.entity.Tenant;
import org.backend.exception.ResourceNotFoundException;
import org.backend.repository.LabelPersonalizationRepository;
import org.backend.repository.LabelRepository;
import org.backend.repository.ModuleRepository;
import org.backend.repository.ScreenRepository;
import org.backend.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelService {

    private final ModuleRepository moduleRepository;
    private final ScreenRepository screenRepository;
    private final LabelRepository labelRepository;
    private final TenantRepository tenantRepository;
    private final LabelPersonalizationRepository labelPersonalizationRepository;

    @Autowired
    public LabelService(
            ModuleRepository moduleRepository,
            ScreenRepository screenRepository,
            LabelRepository labelRepository,
            TenantRepository tenantRepository,
            LabelPersonalizationRepository labelPersonalizationRepository) {
        this.moduleRepository = moduleRepository;
        this.screenRepository = screenRepository;
        this.labelRepository = labelRepository;
        this.tenantRepository = tenantRepository;
        this.labelPersonalizationRepository = labelPersonalizationRepository;
    }

    /**
     * Retrieves all labels for a specific module, tenant, and screen.
     */
    @Transactional(readOnly = true)
    public LabelResponse getLabels(String moduleName, String tenantId, String screenName) {
        ScreenModule module = moduleRepository.findByName(moduleName)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleName));

        Screen screen = screenRepository.findByNameAndScreenModule(screenName, module)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found: " + screenName));

        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));

        List<Label> screenLabels = labelRepository.findByScreen(screen);
        List<LabelPersonalization> personalizations =
                labelPersonalizationRepository.findByTenantAndLabelIn(tenant, screenLabels);

        List<LabelDto> labelDtos = screenLabels.stream()
                .map(label -> {
                    String personalizedName = personalizations.stream()
                            .filter(p -> p.getLabel().getId().equals(label.getId()))
                            .map(LabelPersonalization::getPersonalizedName)
                            .findFirst()
                            .orElse(label.getDefaultName());

                    return new LabelDto(
                            label.getId().toString(),
                            label.getKey(),
                            personalizedName,
                            label.getDefaultName()
                    );
                })
                .collect(Collectors.toList());

        return new LabelResponse(moduleName, screenName, tenantId, labelDtos);
    }

    /**
     * Retrieves a specific label by its key.
     */
    @Transactional(readOnly = true)
    public LabelDto getLabel(String moduleName, String tenantId, String screenName, String labelKey) {
        ScreenModule module = moduleRepository.findByName(moduleName)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleName));

        Screen screen = screenRepository.findByNameAndScreenModule(screenName, module)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found: " + screenName));

        Label label = labelRepository.findByKeyAndScreen(labelKey, screen)
                .orElseThrow(() -> new ResourceNotFoundException("Label not found: " + labelKey));

        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));

        String personalizedName = labelPersonalizationRepository.findByLabelAndTenant(label, tenant)
                .map(LabelPersonalization::getPersonalizedName)
                .orElse(label.getDefaultName());

        return new LabelDto(
                label.getId().toString(),
                label.getKey(),
                personalizedName,
                label.getDefaultName()
        );
    }

    /**
     * Updates or creates a label personalization, creating missing entities if needed.
     */
    @Transactional
    public LabelDto updateLabelPersonalization(
            String moduleName,
            String tenantId,
            String screenName,
            String labelKey,
            String personalizedName,
            String updatedBy) {

        // Step 1: Get or create ScreenModule
        ScreenModule module = moduleRepository.findByName(moduleName)
                .orElseGet(() -> {
                    ScreenModule newModule = new ScreenModule();
                    newModule.setName(moduleName);
                    newModule.setDescription(moduleName + " Module");
                    return moduleRepository.save(newModule);
                });

        // Step 2: Get or create Tenant
        Tenant tenant = tenantRepository.findByTenantId(tenantId)
                .orElseGet(() -> {
                    Tenant newTenant = new Tenant();
                    newTenant.setTenantId(tenantId);
                    newTenant.setName(tenantId + " Tenant");
                    newTenant.setActive(true);
                    return tenantRepository.save(newTenant);
                });

        // Step 3: Get or create Screen
        Screen screen = screenRepository.findByNameAndScreenModule(screenName, module)
                .orElseGet(() -> {
                    Screen newScreen = new Screen();
                    newScreen.setName(screenName);
                    newScreen.setDescription(screenName + " Screen");
                    newScreen.setScreenModule(module);
                    return screenRepository.save(newScreen);
                });

        // Step 4: Get or create Label
        Label label = labelRepository.findByKeyAndScreen(labelKey, screen)
                .orElseGet(() -> {
                    Label newLabel = new Label();
                    newLabel.setKey(labelKey);
                    newLabel.setDefaultName(personalizedName); // Use personalizedName as default
                    newLabel.setDescription(labelKey + " Label");
                    newLabel.setScreen(screen);
                    return labelRepository.save(newLabel);
                });

        // Step 5: Update or create LabelPersonalization
        LabelPersonalization personalization = labelPersonalizationRepository
                .findByLabelAndTenant(label, tenant)
                .orElseGet(() -> {
                    LabelPersonalization newPersonalization = new LabelPersonalization();
                    newPersonalization.setLabel(label);
                    newPersonalization.setTenant(tenant);
                    newPersonalization.setCreatedBy(updatedBy);
                    return newPersonalization;
                });

        personalization.setPersonalizedName(personalizedName);
        if (updatedBy != null) {
            personalization.setUpdatedBy(updatedBy);
        }

        LabelPersonalization savedPersonalization = labelPersonalizationRepository.save(personalization);

        // Step 6: Return LabelDto
        return new LabelDto(
                label.getId().toString(),
                label.getKey(),
                savedPersonalization.getPersonalizedName(),
                label.getDefaultName()
        );
    }
    /**
     * Updates or creates multiple label personalizations, creating missing entities if needed.
     */
    @Transactional
    public LabelResponse updateLabelPersonalizations(List<LabelPersonalizationFullRequest> requests) {
        List<LabelDto> updatedLabels = new ArrayList<>();
        String moduleName = null;
        String tenantId = null;
        String screenName = null;

        for (LabelPersonalizationFullRequest request : requests) {
            LabelDto updatedLabel = updateLabelPersonalization(
                    request.getModuleName(),
                    request.getTenantId(),
                    request.getScreenName(),
                    request.getLabelKey(),
                    request.getPersonalizedName(),
                    request.getUpdatedBy()
            );
            updatedLabels.add(updatedLabel);

            // Store moduleName, tenantId, screenName for response (assuming all requests share these or using the last one)
            moduleName = request.getModuleName();
            tenantId = request.getTenantId();
            screenName = request.getScreenName();
        }

        return new LabelResponse(moduleName, screenName, tenantId, updatedLabels);
    }
}




//package org.backend.service;
//
//import org.backend.dto.LabelDto;
//import org.backend.dto.LabelResponse;
//import org.backend.entity.*;
//import org.backend.exception.ResourceNotFoundException;
//import org.backend.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Service
//public class LabelService {
//
//    private final ModuleRepository moduleRepository;
//    private final ScreenRepository screenRepository;
//    private final LabelRepository labelRepository;
//    private final TenantRepository tenantRepository;
//    private final LabelPersonalizationRepository labelPersonalizationRepository;
//
//    @Autowired
//    public LabelService(
//            ModuleRepository moduleRepository,
//            ScreenRepository screenRepository,
//            LabelRepository labelRepository,
//            TenantRepository tenantRepository,
//            LabelPersonalizationRepository labelPersonalizationRepository) {
//        this.moduleRepository = moduleRepository;
//        this.screenRepository = screenRepository;
//        this.labelRepository = labelRepository;
//        this.tenantRepository = tenantRepository;
//        this.labelPersonalizationRepository = labelPersonalizationRepository;
//    }
//
//    /**
//     * Retrieves all labels for a specific module, tenant, and screen.
//     */
//    @Transactional(readOnly = true)
//    public LabelResponse getLabels(String moduleName, String tenantId, String screenName) {
//        ScreenModule module = moduleRepository.findByName(moduleName)
//                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleName));
//
//        Screen screen = screenRepository.findByNameAndScreenModule(screenName, module)
//                .orElseThrow(() -> new ResourceNotFoundException("Screen not found: " + screenName));
//
//        Tenant tenant = tenantRepository.findByTenantId(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));
//
//        List<Label> screenLabels = labelRepository.findByScreen(screen);
//        List<LabelPersonalization> personalizations =
//                labelPersonalizationRepository.findByTenantAndLabelIn(tenant, screenLabels);
//
//        List<LabelDto> labelDtos = screenLabels.stream()
//                .map(label -> {
//                    String personalizedName = personalizations.stream()
//                            .filter(p -> p.getLabel().getId().equals(label.getId()))
//                            .map(LabelPersonalization::getPersonalizedName)
//                            .findFirst()
//                            .orElse(label.getDefaultName());
//
//                    return new LabelDto(
//                            label.getId().toString(),
//                            label.getKey(),
//                            personalizedName,
//                            label.getDefaultName()
//                    );
//                })
//                .collect(Collectors.toList());
//
//        return new LabelResponse(moduleName, screenName, tenantId, labelDtos);
//    }
//
//    /**
//     * Retrieves a specific label by its key.
//     */
//    @Transactional(readOnly = true)
//    public LabelDto getLabel(String moduleName, String tenantId, String screenName, String labelKey) {
//        ScreenModule module = moduleRepository.findByName(moduleName)
//                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleName));
//
//        Screen screen = screenRepository.findByNameAndScreenModule(screenName, module)
//                .orElseThrow(() -> new ResourceNotFoundException("Screen not found: " + screenName));
//
//        Label label = labelRepository.findByKeyAndScreen(labelKey, screen)
//                .orElseThrow(() -> new ResourceNotFoundException("Label not found: " + labelKey));
//
//        Tenant tenant = tenantRepository.findByTenantId(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));
//
//        String personalizedName = labelPersonalizationRepository.findByLabelAndTenant(label, tenant)
//                .map(LabelPersonalization::getPersonalizedName)
//                .orElse(label.getDefaultName());
//
//        return new LabelDto(
//                label.getId().toString(),
//                label.getKey(),
//                personalizedName,
//                label.getDefaultName()
//        );
//    }
//
//    /**
//     * Updates or creates a label personalization.
//     */
//    @Transactional
//    public LabelDto updateLabelPersonalization(
//            String moduleName,
//            String tenantId,
//            String screenName,
//            String labelKey,
//            String personalizedName,
//            String updatedBy) {
//
//        ScreenModule module = moduleRepository.findByName(moduleName)
//                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + moduleName));
//
//        Screen screen = screenRepository.findByNameAndScreenModule(screenName, module)
//                .orElseThrow(() -> new ResourceNotFoundException("Screen not found: " + screenName));
//
//        Label label = labelRepository.findByKeyAndScreen(labelKey, screen)
//                .orElseThrow(() -> new ResourceNotFoundException("Label not found: " + labelKey));
//
//        Tenant tenant = tenantRepository.findByTenantId(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));
//
//        LabelPersonalization personalization = labelPersonalizationRepository
//                .findByLabelAndTenant(label, tenant)
//                .orElseGet(() -> {
//                    LabelPersonalization newPersonalization = new LabelPersonalization();
//                    newPersonalization.setLabel(label);
//                    newPersonalization.setTenant(tenant);
//                    return newPersonalization;
//                });
//
//        personalization.setPersonalizedName(personalizedName);
//        if (updatedBy != null) {
//            personalization.setUpdatedBy(updatedBy);
//        }
//
//        LabelPersonalization savedPersonalization = labelPersonalizationRepository.save(personalization);
//
//        return new LabelDto(
//                label.getId().toString(),
//                label.getKey(),
//                savedPersonalization.getPersonalizedName(),
//                label.getDefaultName()
//        );
//    }
//}


