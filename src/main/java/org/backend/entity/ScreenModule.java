package org.backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a module in the application.
 */
@Entity
@Table(name = "screen_modules")
public class ScreenModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "screenModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Screen> screens = new HashSet<>();

    // Constructors
    public ScreenModule() {
    }

    public ScreenModule(String name, String description) {
        this.name = name;
        this.description = description;
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

    public Set<Screen> getScreens() {
        return screens;
    }

    public void setScreens(Set<Screen> screens) {
        this.screens = screens;
    }

    // Helper methods to maintain bidirectional relationship
    public void addScreen(Screen screen) {
        screens.add(screen);
        screen.setScreenModule(this);
    }

    public void removeScreen(Screen screen) {
        screens.remove(screen);
        screen.setScreenModule(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScreenModule)) return false;
        ScreenModule screenModule = (ScreenModule) o;
        return id != null && id.equals(screenModule.getId());
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
// * Entity representing a module in the application.
// */
//@Entity
//@Table(name = "screenModules")
//public class ScreenModule {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name", nullable = false, unique = true)
//    private String name;
//
//    @Column(name = "description")
//    private String description;
//
//    @OneToMany(mappedBy = "screenModule", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Screen> screens = new HashSet<>();
//
//    // Constructors
//    public ScreenModule() {
//    }
//
//    public ScreenModule(String name, String description) {
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
//    public Set<Screen> getScreens() {
//        return screens;
//    }
//
//    public void setScreens(Set<Screen> screens) {
//        this.screens = screens;
//    }
//
//    // Helper methods to maintain bidirectional relationship
//    public void addScreen(Screen screen) {
//        screens.add(screen);
//        screen.setModule(this);
//    }
//
//    public void removeScreen(Screen screen) {
//        screens.remove(screen);
//        screen.setModule(null);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof ScreenModule)) return false;
//        ScreenModule screenModule = (ScreenModule) o;
//        return id != null && id.equals(screenModule.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//}