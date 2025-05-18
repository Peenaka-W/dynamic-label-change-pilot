package org.backend.repository;

import org.backend.entity.Label;
import org.backend.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Label entity operations.
 */
@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    /**
     * Find labels by screen.
     *
     * @param screen the screen
     * @return list of labels belonging to the screen
     */
    List<Label> findByScreen(Screen screen);

    /**
     * Find a label by its key and screen.
     *
     * @param key the key of the label
     * @param screen the screen
     * @return an Optional containing the label if found
     */
    Optional<Label> findByKeyAndScreen(String key, Screen screen);

    /**
     * Check if a label exists by its key and screen.
     *
     * @param key the key of the label
     * @param screen the screen
     * @return true if the label exists, false otherwise
     */
    boolean existsByKeyAndScreen(String key, Screen screen);
}