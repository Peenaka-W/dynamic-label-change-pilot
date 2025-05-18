////package org.backend.config;
//////
////////import org.backend.entity.Label;
////////import org.backend.entity.Screen;
////////import org.backend.entity.ScreenModule;
////////import org.backend.entity.Tenant;
////////import org.backend.repository.LabelRepository;
////////import org.backend.repository.ModuleRepository;
////////import org.backend.repository.ScreenRepository;
////////import org.backend.repository.TenantRepository;
////////import org.slf4j.Logger;
////////import org.slf4j.LoggerFactory;
////////import org.springframework.beans.factory.annotation.Autowired;
////////import org.springframework.boot.CommandLineRunner;
////////import org.springframework.stereotype.Component;
////////import org.springframework.transaction.annotation.Transactional;
////////
////////@Component
////////public class DataInitializer implements CommandLineRunner {
////////
////////    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
////////
////////    private final ModuleRepository moduleRepository;
////////    private final ScreenRepository screenRepository;
////////    private final LabelRepository labelRepository;
////////    private final TenantRepository tenantRepository;
////////
////////    @Autowired
////////    public DataInitializer(ModuleRepository moduleRepository, ScreenRepository screenRepository, LabelRepository labelRepository, TenantRepository tenantRepository) {
////////        this.moduleRepository = moduleRepository;
////////        this.screenRepository = screenRepository;
////////        this.labelRepository = labelRepository;
////////        this.tenantRepository = tenantRepository;
////////    }
////////
////////    @Override
////////    @Transactional
////////    public void run(String... args) throws Exception {
////////        logger.info("Starting data initialization");
////////
////////        // Initialize tenant
////////        if (!tenantRepository.existsByTenantId("tenant123")) {
////////            Tenant tenant = new Tenant("tenant123", "Tenant 123");
////////            tenantRepository.saveAndFlush(tenant);
////////            logger.info("Initialized tenant: tenant123");
////////        }
////////
////////        // Initialize module, screen, and label
////////        ScreenModule lendingModule;
////////        if (!moduleRepository.existsByName("lending")) {
////////            lendingModule = new ScreenModule("lending", "Lending module description");
////////            lendingModule = moduleRepository.saveAndFlush(lendingModule); // Ensure the module is persisted and has an ID
////////            logger.info("Initialized module: lending with ID: {}", lendingModule.getId());
////////        } else {
////////            logger.info("Module 'lending' already exists, fetching it");
////////            lendingModule = moduleRepository.findByName("lending").orElseThrow(() -> new RuntimeException("Module 'lending' not found after existence check"));
////////            logger.info("Fetched module: lending with ID: {}", lendingModule.getId());
////////        }
////////
////////        // Initialize screen
////////        if (!screenRepository.existsByNameAndScreenModule("dashboard", lendingModule)) {
////////            Screen dashboardScreen = new Screen("dashboard", "Dashboard screen description");
////////            dashboardScreen.setScreenModule(lendingModule);
////////            screenRepository.saveAndFlush(dashboardScreen); // Persist the screen
////////            logger.info("Initialized screen: dashboard under module: lending with module ID: {}", lendingModule.getId());
////////
////////            Label submitLabel = new Label("lbl_submit", "Submit", "Submit button label");
////////            submitLabel.setScreen(dashboardScreen);
////////            labelRepository.saveAndFlush(submitLabel);
////////            logger.info("Initialized label: lbl_submit under screen: dashboard");
////////        } else {
////////            logger.info("Screen 'dashboard' already exists under module 'lending'");
////////        }
////////
////////        logger.info("Data initialization completed");
////////    }
////////}
//////
//////
//////
////
//
//
//package org.backend.config;
//import org.backend.entity.ScreenModule;
//import org.backend.entity.Screen;
//import org.backend.entity.Label;
//import org.backend.entity.Tenant;
//import org.backend.repository.ModuleRepository;
//import org.backend.repository.ScreenRepository;
//import org.backend.repository.LabelRepository;
//import org.backend.repository.TenantRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
//
//    @Autowired
//    private ModuleRepository moduleRepository;
//
//    @Autowired
//    private ScreenRepository screenRepository;
//
//    @Autowired
//    private LabelRepository labelRepository;
//
//    @Autowired
//    private TenantRepository tenantRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        logger.info("Initializing database with required data...");
//
//        // Create module
//        ScreenModule lendingModule = new ScreenModule();
//        lendingModule.setName("lending");
//        lendingModule.setDescription("Lending Module");
//        moduleRepository.save(lendingModule);
//
//        // Create screen
//        Screen dashboardScreen = new Screen();
//        dashboardScreen.setName("dashboard");
//        dashboardScreen.setScreenModule(lendingModule);
//        dashboardScreen.setDescription("Dashboard Screen");
//        screenRepository.save(dashboardScreen);
//
//        // Create label
//        Label submitLabel = new Label();
//        submitLabel.setKey("lbl_submit");
//        submitLabel.setDefaultName("Submit");
//        submitLabel.setScreen(dashboardScreen);
//        labelRepository.save(submitLabel);
//
//        // Create tenant
//        Tenant tenant = new Tenant();
//        tenant.setTenantId("tenant123");
//        tenant.setName("Test Tenant");
//        tenantRepository.save(tenant);
//
//        logger.info("Database initialization completed successfully.");
//    }
//}
//
//
//
//
//// JSON INITIALIZER
////package org.backend.config;
////
////import com.fasterxml.jackson.databind.ObjectMapper;
////import org.backend.entity.*;
////import org.backend.repository.*;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.CommandLineRunner;
////import org.springframework.core.io.ClassPathResource;
////import org.springframework.stereotype.Component;
////
////import java.io.InputStream;
////import java.time.LocalDateTime;
////import java.util.HashMap;
////import java.util.Map;
////
////@Component
////public class DataInitializer implements CommandLineRunner {
////
////    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
////
////    @Autowired
////    private ModuleRepository moduleRepository;
////    @Autowired private ScreenRepository screenRepository;
////    @Autowired private LabelRepository labelRepository;
////    @Autowired private TenantRepository tenantRepository;
////    @Autowired private LabelPersonalizationRepository labelPersonalizationRepository;
////
////    @Override
////    public void run(String... args) throws Exception {
////        logger.info("⏳ Loading label config from JSON...");
////
////        ObjectMapper mapper = new ObjectMapper();
////        InputStream inputStream = new ClassPathResource("labels-config.json").getInputStream();
////        LabelConfig config = mapper.readValue(inputStream, LabelConfig.class);
////
////        // Ensure base module exists
////        ScreenModule module = moduleRepository.findByName("lending")
////                .orElseGet(() -> moduleRepository.save(new ScreenModule("lending", "Lending module")));
////
////        Map<String, Map<String, Map<String, LabelConfig.LabelData>>> allScreens = Map.of(
////                "leadGeneration", config.getLeadGeneration(),
////                "customerDetails", config.getCustomerDetails()
////        );
////
////        for (Map.Entry<String, Map<String, Map<String, LabelConfig.LabelData>>> screenEntry : allScreens.entrySet()) {
////            String screenName = screenEntry.getKey();
////
////            Screen screen = screenRepository.findByNameAndScreenModule(screenName, module)
////                    .orElseGet(() -> screenRepository.save(new Screen(screenName, screenName + " screen", module)));
////
////            // Map to cache already-saved Labels (key + screen)
////            Map<String, Label> savedLabels = new HashMap<>();
////
////            for (Map.Entry<String, Map<String, LabelConfig.LabelData>> tenantEntry : screenEntry.getValue().entrySet()) {
////                String tenantId = tenantEntry.getKey();
////
////                Tenant tenant = tenantRepository.findByTenantId(tenantId)
////                        .orElseGet(() -> tenantRepository.save(new Tenant(tenantId, tenantId)));
////
////                for (LabelConfig.LabelData labelData : tenantEntry.getValue().values()) {
////                    // Reuse label if already created for this screen + key
////                    String labelKey = labelData.getKey();
////                    Label label = savedLabels.computeIfAbsent(labelKey, key -> {
////                        return labelRepository.findByKeyAndScreen(labelKey, screen)
////                                .orElseGet(() -> {
////                                    Label newLabel = new Label();
////                                    newLabel.setKey(labelKey);
////                                    newLabel.setDefaultName(labelData.getDefaultName());
////                                    newLabel.setDescription(null);
////                                    newLabel.setScreen(screen);
////                                    return labelRepository.save(newLabel);
////                                });
////                    });
////
////                    // Avoid duplicate personalization
////                    boolean exists = labelPersonalizationRepository
////                            .findByLabelAndTenant(label, tenant)
////                            .isPresent();
////
////                    if (!exists) {
////                        LabelPersonalization personalization = new LabelPersonalization();
////                        personalization.setLabel(label);
////                        personalization.setTenant(tenant);
////                        personalization.setPersonalizedName(labelData.getName());
////                        personalization.setCreatedAt(LocalDateTime.now());
////
////                        labelPersonalizationRepository.save(personalization);
////                    }
////                }
////            }
////        }
////
////        logger.info("✅ Label and personalization initialization complete.");
////    }
////}
