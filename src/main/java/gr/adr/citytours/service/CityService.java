package gr.adr.citytours.service;

import gr.adr.citytours.domain.City;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.citytours.domain.City}.
 */
public interface CityService {
    /**
     * Save a city.
     *
     * @param city the entity to save.
     * @return the persisted entity.
     */
    City save(City city);

    /**
     * Updates a city.
     *
     * @param city the entity to update.
     * @return the persisted entity.
     */
    City update(City city);

    /**
     * Partially updates a city.
     *
     * @param city the entity to update partially.
     * @return the persisted entity.
     */
    Optional<City> partialUpdate(City city);

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<City> findAll(Pageable pageable);

    /**
     * Get the "id" city.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<City> findOne(Long id);

    /**
     * Delete the "id" city.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
