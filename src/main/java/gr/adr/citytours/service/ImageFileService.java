package gr.adr.citytours.service;

import gr.adr.citytours.domain.ImageFile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.citytours.domain.ImageFile}.
 */
public interface ImageFileService {
    /**
     * Save a imageFile.
     *
     * @param imageFile the entity to save.
     * @return the persisted entity.
     */
    ImageFile save(ImageFile imageFile);

    /**
     * Updates a imageFile.
     *
     * @param imageFile the entity to update.
     * @return the persisted entity.
     */
    ImageFile update(ImageFile imageFile);

    /**
     * Partially updates a imageFile.
     *
     * @param imageFile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ImageFile> partialUpdate(ImageFile imageFile);

    /**
     * Get all the imageFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageFile> findAll(Pageable pageable);

    /**
     * Get all the imageFiles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageFile> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" imageFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImageFile> findOne(Long id);

    /**
     * Delete the "id" imageFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
