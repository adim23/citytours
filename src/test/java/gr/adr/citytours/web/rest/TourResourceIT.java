package gr.adr.citytours.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gr.adr.citytours.IntegrationTest;
import gr.adr.citytours.domain.Tour;
import gr.adr.citytours.repository.TourRepository;
import gr.adr.citytours.service.TourService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TourResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Boolean DEFAULT_PET_FRIENDLY = false;
    private static final Boolean UPDATED_PET_FRIENDLY = true;

    private static final Boolean DEFAULT_KIDS_ALLOWED = false;
    private static final Boolean UPDATED_KIDS_ALLOWED = true;

    private static final LocalDate DEFAULT_AVAILABLE_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AVAILABLE_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AVAILABLE_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AVAILABLE_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String ENTITY_API_URL = "/api/tours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TourRepository tourRepository;

    @Mock
    private TourRepository tourRepositoryMock;

    @Mock
    private TourService tourServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourMockMvc;

    private Tour tour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tour createEntity(EntityManager em) {
        Tour tour = new Tour()
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .duration(DEFAULT_DURATION)
            .petFriendly(DEFAULT_PET_FRIENDLY)
            .kidsAllowed(DEFAULT_KIDS_ALLOWED)
            .availableFromDate(DEFAULT_AVAILABLE_FROM_DATE)
            .availableToDate(DEFAULT_AVAILABLE_TO_DATE)
            .enabled(DEFAULT_ENABLED);
        return tour;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tour createUpdatedEntity(EntityManager em) {
        Tour tour = new Tour()
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .duration(UPDATED_DURATION)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .enabled(UPDATED_ENABLED);
        return tour;
    }

    @BeforeEach
    public void initTest() {
        tour = createEntity(em);
    }

    @Test
    @Transactional
    void createTour() throws Exception {
        int databaseSizeBeforeCreate = tourRepository.findAll().size();
        // Create the Tour
        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isCreated());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeCreate + 1);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTour.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTour.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTour.getPetFriendly()).isEqualTo(DEFAULT_PET_FRIENDLY);
        assertThat(testTour.getKidsAllowed()).isEqualTo(DEFAULT_KIDS_ALLOWED);
        assertThat(testTour.getAvailableFromDate()).isEqualTo(DEFAULT_AVAILABLE_FROM_DATE);
        assertThat(testTour.getAvailableToDate()).isEqualTo(DEFAULT_AVAILABLE_TO_DATE);
        assertThat(testTour.getEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    void createTourWithExistingId() throws Exception {
        // Create the Tour with an existing ID
        tour.setId(1L);

        int databaseSizeBeforeCreate = tourRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourRepository.findAll().size();
        // set the field null
        tour.setTitle(null);

        // Create the Tour, which fails.

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourRepository.findAll().size();
        // set the field null
        tour.setDuration(null);

        // Create the Tour, which fails.

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPetFriendlyIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourRepository.findAll().size();
        // set the field null
        tour.setPetFriendly(null);

        // Create the Tour, which fails.

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKidsAllowedIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourRepository.findAll().size();
        // set the field null
        tour.setKidsAllowed(null);

        // Create the Tour, which fails.

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourRepository.findAll().size();
        // set the field null
        tour.setEnabled(null);

        // Create the Tour, which fails.

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTours() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get all the tourList
        restTourMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tour.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].petFriendly").value(hasItem(DEFAULT_PET_FRIENDLY.booleanValue())))
            .andExpect(jsonPath("$.[*].kidsAllowed").value(hasItem(DEFAULT_KIDS_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].availableFromDate").value(hasItem(DEFAULT_AVAILABLE_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].availableToDate").value(hasItem(DEFAULT_AVAILABLE_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllToursWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllToursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get the tour
        restTourMockMvc
            .perform(get(ENTITY_API_URL_ID, tour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tour.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.petFriendly").value(DEFAULT_PET_FRIENDLY.booleanValue()))
            .andExpect(jsonPath("$.kidsAllowed").value(DEFAULT_KIDS_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.availableFromDate").value(DEFAULT_AVAILABLE_FROM_DATE.toString()))
            .andExpect(jsonPath("$.availableToDate").value(DEFAULT_AVAILABLE_TO_DATE.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTour() throws Exception {
        // Get the tour
        restTourMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // Update the tour
        Tour updatedTour = tourRepository.findById(tour.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTour are not directly saved in db
        em.detach(updatedTour);
        updatedTour
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .duration(UPDATED_DURATION)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .enabled(UPDATED_ENABLED);

        restTourMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTour.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTour))
            )
            .andExpect(status().isOk());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTour.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTour.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTour.getPetFriendly()).isEqualTo(UPDATED_PET_FRIENDLY);
        assertThat(testTour.getKidsAllowed()).isEqualTo(UPDATED_KIDS_ALLOWED);
        assertThat(testTour.getAvailableFromDate()).isEqualTo(UPDATED_AVAILABLE_FROM_DATE);
        assertThat(testTour.getAvailableToDate()).isEqualTo(UPDATED_AVAILABLE_TO_DATE);
        assertThat(testTour.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void putNonExistingTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();
        tour.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tour.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tour))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();
        tour.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tour))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();
        tour.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourWithPatch() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // Update the tour using partial update
        Tour partialUpdatedTour = new Tour();
        partialUpdatedTour.setId(tour.getId());

        partialUpdatedTour
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .enabled(UPDATED_ENABLED);

        restTourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTour.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTour))
            )
            .andExpect(status().isOk());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTour.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTour.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTour.getPetFriendly()).isEqualTo(DEFAULT_PET_FRIENDLY);
        assertThat(testTour.getKidsAllowed()).isEqualTo(DEFAULT_KIDS_ALLOWED);
        assertThat(testTour.getAvailableFromDate()).isEqualTo(UPDATED_AVAILABLE_FROM_DATE);
        assertThat(testTour.getAvailableToDate()).isEqualTo(UPDATED_AVAILABLE_TO_DATE);
        assertThat(testTour.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void fullUpdateTourWithPatch() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // Update the tour using partial update
        Tour partialUpdatedTour = new Tour();
        partialUpdatedTour.setId(tour.getId());

        partialUpdatedTour
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .duration(UPDATED_DURATION)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .enabled(UPDATED_ENABLED);

        restTourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTour.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTour))
            )
            .andExpect(status().isOk());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTour.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTour.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTour.getPetFriendly()).isEqualTo(UPDATED_PET_FRIENDLY);
        assertThat(testTour.getKidsAllowed()).isEqualTo(UPDATED_KIDS_ALLOWED);
        assertThat(testTour.getAvailableFromDate()).isEqualTo(UPDATED_AVAILABLE_FROM_DATE);
        assertThat(testTour.getAvailableToDate()).isEqualTo(UPDATED_AVAILABLE_TO_DATE);
        assertThat(testTour.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void patchNonExistingTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();
        tour.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tour.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tour))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();
        tour.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tour))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();
        tour.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        int databaseSizeBeforeDelete = tourRepository.findAll().size();

        // Delete the tour
        restTourMockMvc
            .perform(delete(ENTITY_API_URL_ID, tour.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
