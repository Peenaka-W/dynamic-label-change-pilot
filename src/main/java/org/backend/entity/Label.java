package org.backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a label that can be displayed on a screen.
 */
@Entity
@Table(name = "labels", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"label_key", "screen_id"})
})
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label_key", nullable = false)
    private String key;

    @Column(name = "default_name", nullable = false)
    private String defaultName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @OneToMany(mappedBy = "label", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LabelPersonalization> personalizations = new HashSet<>();

    // Constructors
    public Label() {
    }

    public Label(String key, String defaultName, String description) {
        this.key = key;
        this.defaultName = defaultName;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Set<LabelPersonalization> getPersonalizations() {
        return personalizations;
    }

    public void setPersonalizations(Set<LabelPersonalization> personalizations) {
        this.personalizations = personalizations;
    }

    // Helper methods to maintain bidirectional relationship
    public void addPersonalization(LabelPersonalization personalization) {
        personalizations.add(personalization);
        personalization.setLabel(this);
    }

    public void removePersonalization(LabelPersonalization personalization) {
        personalizations.remove(personalization);
        personalization.setLabel(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label)) return false;
        Label label = (Label) o;
        return id != null && id.equals(label.getId());
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
// * Entity representing a label that can be displayed on a screen.
// */
//@Entity
//@Table(name = "labels", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"key", "screen_id"})
//})
//public class Label {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "key", nullable = false)
//    private String key;
//
//    @Column(name = "default_name", nullable = false)
//    private String defaultName;
//
//    @Column(name = "description")
//    private String description;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "screen_id", nullable = false)
//    private Screen screen;
//
//    @OneToMany(mappedBy = "label", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<LabelPersonalization> personalizations = new HashSet<>();
//
//    // Constructors
//    public Label() {
//    }
//
//    public Label(String key, String defaultName, String description) {
//        this.key = key;
//        this.defaultName = defaultName;
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
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public String getDefaultName() {
//        return defaultName;
//    }
//
//    public void setDefaultName(String defaultName) {
//        this.defaultName = defaultName;
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
//    public Screen getScreen() {
//        return screen;
//    }
//
//    public void setScreen(Screen screen) {
//        this.screen = screen;
//    }
//
//    public Set<LabelPersonalization> getPersonalizations() {
//        return personalizations;
//    }
//
//    public void setPersonalizations(Set<LabelPersonalization> personalizations) {
//        this.personalizations = personalizations;
//    }
//
//    // Helper methods to maintain bidirectional relationship
//    public void addPersonalization(LabelPersonalization personalization) {
//        personalizations.add(personalization);
//        personalization.setLabel(this);
//    }
//
//    public void removePersonalization(LabelPersonalization personalization) {
//        personalizations.remove(personalization);
//        personalization.setLabel(null);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Label)) return false;
//        Label label = (Label) o;
//        return id != null && id.equals(label.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//}