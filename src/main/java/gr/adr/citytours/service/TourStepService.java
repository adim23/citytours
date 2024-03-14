package gr.adr.citytours.service;

import gr.adr.citytours.domain.TourStep;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.citytours.domain.TourStep}.
 */
public interface TourStepService {
    /**
     * Save a tourStep.
     *
     * @param tourStep the entity to save.
     * @return the persisted entity.
     */
    TourStep save(TourStep tourStep);

    /**
     * Updates a tourStep.
     *
     * @param tourStep the entity to update.
     * @return the persisted entity.
     */
    TourStep update(TourStep tourStep);

    /**
     * Partially updates a tourStep.
     *
     * @param tourStep the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourStep> partialUpdate(TourStep tourStep);

    /**
     * Get all the tourSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourStep> findAll(Pageable pageable);

    /**
     * Get all the tourSteps with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourStep> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tourStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourStep> findOne(Long id);

    /**
     * Delete the "id" tourStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
