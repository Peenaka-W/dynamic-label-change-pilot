package org.backend.repository;

import org.backend.entity.Screen;
import org.backend.entity.ScreenModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Screen entity operations.
 */
@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

    /**
     * Find screens by module.
     *
     * @param screenModule the module
     * @return list of screens belonging to the module
     */
    List<Screen> findByScreenModule(ScreenModule screenModule);

    /**
     * Find a screen by its name and module.
     *
     * @param name the name of the screen
     * @param screenModule the module
     * @return an Optional containing the screen if found
     */
    Optional<Screen> findByNameAndScreenModule(String name, ScreenModule screenModule);

    /**
     * Check if a screen exists by its name and module.
     *
     * @param name the name of the screen
     * @param screenModule the module
     * @return true if the screen exists, false otherwise
     */
    boolean existsByNameAndScreenModule(String name, ScreenModule screenModule);
}














//package org.backend.repository;


//
//
//
//import org.backend.entity.Screen;
//import org.backend.entity.ScreenModule;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * Repository for Screen entity operations.
// */
//@Repository
//public interface ScreenRepository extends JpaRepository<Screen, Long> {
//
//    /**
//     * Find screens by module.
//     *
//     * @param screenModule the module
//     * @return list of screens belonging to the module
//     */
//    List<Screen> findByModule(ScreenModule screenModule);
//
//    /**
//     * Find a screen by its name and module.
//     *
//     * @param name the name of the screen
//     * @param screenModule the module
//     * @return an Optional containing the screen if found
//     */
//    Optional<Screen> findByNameAndModule(String name, ScreenModule screenModule);
//
//    /**
//     * Check if a screen exists by its name and module.
//     *
//     * @param name the name of the screen
//     * @param screenModule the module
//     * @return true if the screen exists, false otherwise
//     */
//    boolean existsByNameAndModule(String name, ScreenModule screenModule);
//}