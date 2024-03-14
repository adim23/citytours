package gr.adr.citytours.service;

import gr.adr.citytours.domain.Place;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.citytours.domain.Place}.
 */
public interface PlaceService {
    /**
     * Save a place.
     *
     * @param place the entity to save.
     * @return the persisted entity.
     */
    Place save(Place place);

    /**
     * Updates a place.
     *
     * @param place the entity to update.
     * @return the persisted entity.
     */
    Place update(Place place);

    /**
     * Partially updates a place.
     *
     * @param place the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Place> partialUpdate(Place place);

    /**
     * Get all the places.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Place> findAll(Pageable pageable);

    /**
     * Get all the places with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Place> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" place.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Place> findOne(Long id);

    /**
     * Delete the "id" place.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
