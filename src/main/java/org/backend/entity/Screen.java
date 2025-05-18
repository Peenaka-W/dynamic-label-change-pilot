package org.backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a screen within a module.
 */
@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_module_id", nullable = false)
    private ScreenModule screenModule;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Label> labels = new HashSet<>();

    // Constructors
    public Screen() {
    }

    public Screen(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Screen(String screenName, String s, ScreenModule module) {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScreenModule getScreenModule() {
        return screenModule;
    }

    public void setScreenModule(ScreenModule screenModule) {
        this.screenModule = screenModule;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    // Helper methods to maintain bidirectional relationship
    public void addLabel(Label label) {
        labels.add(label);
        label.setScreen(this);
    }

    public void removeLabel(Label label) {
        labels.remove(label);
        label.setScreen(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Screen)) return false;
        Screen screen = (Screen) o;
        return id != null && id.equals(screen.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


























//package org.backend.entity;


//
//import jakarta.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Entity representing a screen within a module.
// */
//@Entity
//@Table(name = "screens")
//public class Screen {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "description")
//    private String description;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "module_id", nullable = false)
//    private ScreenModule screenModule;
//
//    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Label> labels = new HashSet<>();
//
//    // Constructors
//    public Screen() {
//    }
//
//    public Screen(String name, String description) {
//        this.name = name;
//        this.description = description;
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public ScreenModule getModule() {
//        return screenModule;
//    }
//
//    public void setModule(ScreenModule screenModule) {
//        this.screenModule = screenModule;
//    }
//
//    public Set<Label> getLabels() {
//        return labels;
//    }
//
//    public void setLabels(Set<Label> labels) {
//        this.labels = labels;
//    }
//
//    // Helper methods to maintain bidirectional relationship
//    public void addLabel(Label label) {
//        labels.add(label);
//        label.setScreen(this);
//    }
//
//    public void removeLabel(Label label) {
//        labels.remove(label);
//        label.setScreen(null);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Screen)) return false;
//        Screen screen = (Screen) o;
//        return id != null && id.equals(screen.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//}