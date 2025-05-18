package org.backend.repository;

import org.backend.entity.ScreenModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Module entity operations.
 */
@Repository
public interface ModuleRepository extends JpaRepository<ScreenModule, Long> {

    /**
     * Find a module by its name.
     *
     * @param name the name of the module
     * @return an Optional containing the module if found
     */
    Optional<ScreenModule> findByName(String name);

    /**
     * Check if a module exists by its name.
     *
     * @param name the name of the module
     * @return true if the module exists, false otherwise
     */
    boolean existsByName(String name);
}