package gr.adr.citytours.web.rest;

import gr.adr.citytours.domain.ImageFile;
import gr.adr.citytours.repository.ImageFileRepository;
import gr.adr.citytours.service.ImageFileService;
import gr.adr.citytours.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link gr.adr.citytours.domain.ImageFile}.
 */
@RestController
@RequestMapping("/api/image-files")
public class ImageFileResource {

    private final Logger log = LoggerFactory.getLogger(ImageFileResource.class);

    private static final String ENTITY_NAME = "imageFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImageFileService imageFileService;

    private final ImageFileRepository imageFileRepository;

    public ImageFileResource(ImageFileService imageFileService, ImageFileRepository imageFileRepository) {
        this.imageFileService = imageFileService;
        this.imageFileRepository = imageFileRepository;
    }

    /**
     * {@code POST  /image-files} : Create a new imageFile.
     *
     * @param imageFile the imageFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageFile, or with status {@code 400 (Bad Request)} if the imageFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ImageFile> createImageFile(@RequestBody ImageFile imageFile) throws URISyntaxException {
        log.debug("REST request to save ImageFile : {}", imageFile);
        if (imageFile.getId() != null) {
            throw new BadRequestAlertException("A new imageFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageFile result = imageFileService.save(imageFile);
        return ResponseEntity
            .created(new URI("/api/image-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /image-files/:id} : Updates an existing imageFile.
     *
     * @param id the id of the imageFile to save.
     * @param imageFile the imageFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageFile,
     * or with status {@code 400 (Bad Request)} if the imageFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imageFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ImageFile> updateImageFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImageFile imageFile
    ) throws URISyntaxException {
        log.debug("REST request to update ImageFile : {}, {}", id, imageFile);
        if (imageFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imageFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imageFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImageFile result = imageFileService.update(imageFile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /image-files/:id} : Partial updates given fields of an existing imageFile, field will ignore if it is null
     *
     * @param id the id of the imageFile to save.
     * @param imageFile the imageFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageFile,
     * or with status {@code 400 (Bad Request)} if the imageFile is not valid,
     * or with status {@code 404 (Not Found)} if the imageFile is not found,
     * or with status {@code 500 (Internal Server Error)} if the imageFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImageFile> partialUpdateImageFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImageFile imageFile
    ) throws URISyntaxException {
        log.debug("REST request to partial update ImageFile partially : {}, {}", id, imageFile);
        if (imageFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, imageFile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!imageFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImageFile> result = imageFileService.partialUpdate(imageFile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageFile.getId().toString())
        );
    }

    /**
     * {@code GET  /image-files} : get all the imageFiles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imageFiles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ImageFile>> getAllImageFiles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ImageFiles");
        Page<ImageFile> page;
        if (eagerload) {
            page = imageFileService.findAllWithEagerRelationships(pageable);
        } else {
            page = imageFileService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /image-files/:id} : get the "id" imageFile.
     *
     * @param id the id of the imageFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ImageFile> getImageFile(@PathVariable("id") Long id) {
        log.debug("REST request to get ImageFile : {}", id);
        Optional<ImageFile> imageFile = imageFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageFile);
    }

    /**
     * {@code DELETE  /image-files/:id} : delete the "id" imageFile.
     *
     * @param id the id of the imageFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImageFile(@PathVariable("id") Long id) {
        log.debug("REST request to delete ImageFile : {}", id);
        imageFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
