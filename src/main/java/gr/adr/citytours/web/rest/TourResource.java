package gr.adr.citytours.web.rest;

import gr.adr.citytours.domain.Tour;
import gr.adr.citytours.repository.TourRepository;
import gr.adr.citytours.service.TourService;
import gr.adr.citytours.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link gr.adr.citytours.domain.Tour}.
 */
@RestController
@RequestMapping("/api/tours")
public class TourResource {

    private final Logger log = LoggerFactory.getLogger(TourResource.class);

    private static final String ENTITY_NAME = "tour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourService tourService;

    private final TourRepository tourRepository;

    public TourResource(TourService tourService, TourRepository tourRepository) {
        this.tourService = tourService;
        this.tourRepository = tourRepository;
    }

    /**
     * {@code POST  /tours} : Create a new tour.
     *
     * @param tour the tour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tour, or with status {@code 400 (Bad Request)} if the tour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tour> createTour(@Valid @RequestBody Tour tour) throws URISyntaxException {
        log.debug("REST request to save Tour : {}", tour);
        if (tour.getId() != null) {
            throw new BadRequestAlertException("A new tour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tour result = tourService.save(tour);
        return ResponseEntity
            .created(new URI("/api/tours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tours/:id} : Updates an existing tour.
     *
     * @param id the id of the tour to save.
     * @param tour the tour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tour,
     * or with status {@code 400 (Bad Request)} if the tour is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Tour tour)
        throws URISyntaxException {
        log.debug("REST request to update Tour : {}, {}", id, tour);
        if (tour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tour.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tour result = tourService.update(tour);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tour.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tours/:id} : Partial updates given fields of an existing tour, field will ignore if it is null
     *
     * @param id the id of the tour to save.
     * @param tour the tour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tour,
     * or with status {@code 400 (Bad Request)} if the tour is not valid,
     * or with status {@code 404 (Not Found)} if the tour is not found,
     * or with status {@code 500 (Internal Server Error)} if the tour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tour> partialUpdateTour(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tour tour
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tour partially : {}, {}", id, tour);
        if (tour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tour.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tour> result = tourService.partialUpdate(tour);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tour.getId().toString())
        );
    }

    /**
     * {@code GET  /tours} : get all the tours.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tours in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Tour>> getAllTours(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Tours");
        Page<Tour> page;
        if (eagerload) {
            page = tourService.findAllWithEagerRelationships(pageable);
        } else {
            page = tourService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tours/:id} : get the "id" tour.
     *
     * @param id the id of the tour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTour(@PathVariable("id") Long id) {
        log.debug("REST request to get Tour : {}", id);
        Optional<Tour> tour = tourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tour);
    }

    /**
     * {@code DELETE  /tours/:id} : delete the "id" tour.
     *
     * @param id the id of the tour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tour : {}", id);
        tourService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
