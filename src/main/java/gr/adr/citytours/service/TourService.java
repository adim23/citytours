package gr.adr.citytours.service;

import gr.adr.citytours.domain.Tour;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.citytours.domain.Tour}.
 */
public interface TourService {
    /**
     * Save a tour.
     *
     * @param tour the entity to save.
     * @return the persisted entity.
     */
    Tour save(Tour tour);

    /**
     * Updates a tour.
     *
     * @param tour the entity to update.
     * @return the persisted entity.
     */
    Tour update(Tour tour);

    /**
     * Partially updates a tour.
     *
     * @param tour the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tour> partialUpdate(Tour tour);

    /**
     * Get all the tours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tour> findAll(Pageable pageable);

    /**
     * Get all the tours with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tour> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tour.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tour> findOne(Long id);

    /**
     * Delete the "id" tour.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
