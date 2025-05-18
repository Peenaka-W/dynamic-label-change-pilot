//package org.backend.config;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import java.util.Map;
//
///**
// * Config class to map the structure of labels-config.json
// * {
// *   "leadGeneration": {
// *     "tenant1": {
// *       "leadTitle": { "key": "leadTitle", "name": "Lead Title", "defaultName": "Lead Title" },
// *       ...
// *     },
// *     ...
// *   },
// *   "customerDetails": {
// *     "tenant1": {
// *       ...
// *     }
// *   }
// * }
// */
//public class LabelConfig {
//
//    @JsonProperty("leadGeneration")
//    private Map<String, Map<String, LabelData>> leadGeneration;
//
//    @JsonProperty("customerDetails")
//    private Map<String, Map<String, LabelData>> customerDetails;
//
//    // Getters and setters
//    public Map<String, Map<String, LabelData>> getLeadGeneration() {
//        return leadGeneration;
//    }
//
//    public void setLeadGeneration(Map<String, Map<String, LabelData>> leadGeneration) {
//        this.leadGeneration = leadGeneration;
//    }
//
//    public Map<String, Map<String, LabelData>> getCustomerDetails() {
//        return customerDetails;
//    }
//
//    public void setCustomerDetails(Map<String, Map<String, LabelData>> customerDetails) {
//        this.customerDetails = customerDetails;
//    }
//
//    public static class LabelData {
//        private String key;
//        private String name;
//        private String defaultName;
//
//        // Getters and setters
//        public String getKey() {
//            return key;
//        }
//
//        public void setKey(String key) {
//            this.key = key;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getDefaultName() {
//            return defaultName;
//        }
//
//        public void setDefaultName(String defaultName) {
//            this.defaultName = defaultName;
//        }
//    }
//}
