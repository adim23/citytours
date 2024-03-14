package gr.adr.citytours.service;

import gr.adr.citytours.domain.Passenger;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.citytours.domain.Passenger}.
 */
public interface PassengerService {
    /**
     * Save a passenger.
     *
     * @param passenger the entity to save.
     * @return the persisted entity.
     */
    Passenger save(Passenger passenger);

    /**
     * Updates a passenger.
     *
     * @param passenger the entity to update.
     * @return the persisted entity.
     */
    Passenger update(Passenger passenger);

    /**
     * Partially updates a passenger.
     *
     * @param passenger the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Passenger> partialUpdate(Passenger passenger);

    /**
     * Get all the passengers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Passenger> findAll(Pageable pageable);

    /**
     * Get the "id" passenger.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Passenger> findOne(Long id);

    /**
     * Delete the "id" passenger.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
