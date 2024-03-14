package gr.adr.citytours.service;

import gr.adr.citytours.domain.TourSchedule;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.citytours.domain.TourSchedule}.
 */
public interface TourScheduleService {
    /**
     * Save a tourSchedule.
     *
     * @param tourSchedule the entity to save.
     * @return the persisted entity.
     */
    TourSchedule save(TourSchedule tourSchedule);

    /**
     * Updates a tourSchedule.
     *
     * @param tourSchedule the entity to update.
     * @return the persisted entity.
     */
    TourSchedule update(TourSchedule tourSchedule);

    /**
     * Partially updates a tourSchedule.
     *
     * @param tourSchedule the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourSchedule> partialUpdate(TourSchedule tourSchedule);

    /**
     * Get all the tourSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourSchedule> findAll(Pageable pageable);

    /**
     * Get all the tourSchedules with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourSchedule> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tourSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourSchedule> findOne(Long id);

    /**
     * Delete the "id" tourSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
