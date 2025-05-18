package org.backend.dto;

import java.io.Serializable;

public class LabelDto implements Serializable {

    private String id;
    private String key;
    private String name;
    private String defaultName;

    // Constructors
    public LabelDto() {
    }

    public LabelDto(String id, String key, String name, String defaultName) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.defaultName = defaultName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }
}