package gr.adr.citytours.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gr.adr.citytours.IntegrationTest;
import gr.adr.citytours.domain.TourSchedule;
import gr.adr.citytours.repository.TourScheduleRepository;
import gr.adr.citytours.service.TourScheduleService;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TourScheduleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourScheduleResourceIT {

    private static final Instant DEFAULT_START_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NO_PASSENGERS = 1;
    private static final Integer UPDATED_NO_PASSENGERS = 2;

    private static final Integer DEFAULT_NO_KIDS = 1;
    private static final Integer UPDATED_NO_KIDS = 2;

    private static final Integer DEFAULT_NO_PETS = 1;
    private static final Integer UPDATED_NO_PETS = 2;

    private static final String DEFAULT_START_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_START_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_END_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_END_PLACE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tour-schedules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    @Mock
    private TourScheduleRepository tourScheduleRepositoryMock;

    @Mock
    private TourScheduleService tourScheduleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourScheduleMockMvc;

    private TourSchedule tourSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourSchedule createEntity(EntityManager em) {
        TourSchedule tourSchedule = new TourSchedule()
            .startDatetime(DEFAULT_START_DATETIME)
            .noPassengers(DEFAULT_NO_PASSENGERS)
            .noKids(DEFAULT_NO_KIDS)
            .noPets(DEFAULT_NO_PETS)
            .startPlace(DEFAULT_START_PLACE)
            .endPlace(DEFAULT_END_PLACE);
        return tourSchedule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourSchedule createUpdatedEntity(EntityManager em) {
        TourSchedule tourSchedule = new TourSchedule()
            .startDatetime(UPDATED_START_DATETIME)
            .noPassengers(UPDATED_NO_PASSENGERS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .startPlace(UPDATED_START_PLACE)
            .endPlace(UPDATED_END_PLACE);
        return tourSchedule;
    }

    @BeforeEach
    public void initTest() {
        tourSchedule = createEntity(em);
    }

    @Test
    @Transactional
    void createTourSchedule() throws Exception {
        int databaseSizeBeforeCreate = tourScheduleRepository.findAll().size();
        // Create the TourSchedule
        restTourScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourSchedule)))
            .andExpect(status().isCreated());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        TourSchedule testTourSchedule = tourScheduleList.get(tourScheduleList.size() - 1);
        assertThat(testTourSchedule.getStartDatetime()).isEqualTo(DEFAULT_START_DATETIME);
        assertThat(testTourSchedule.getNoPassengers()).isEqualTo(DEFAULT_NO_PASSENGERS);
        assertThat(testTourSchedule.getNoKids()).isEqualTo(DEFAULT_NO_KIDS);
        assertThat(testTourSchedule.getNoPets()).isEqualTo(DEFAULT_NO_PETS);
        assertThat(testTourSchedule.getStartPlace()).isEqualTo(DEFAULT_START_PLACE);
        assertThat(testTourSchedule.getEndPlace()).isEqualTo(DEFAULT_END_PLACE);
    }

    @Test
    @Transactional
    void createTourScheduleWithExistingId() throws Exception {
        // Create the TourSchedule with an existing ID
        tourSchedule.setId(1L);

        int databaseSizeBeforeCreate = tourScheduleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourScheduleRepository.findAll().size();
        // set the field null
        tourSchedule.setStartDatetime(null);

        // Create the TourSchedule, which fails.

        restTourScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourSchedule)))
            .andExpect(status().isBadRequest());

        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTourSchedules() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        // Get all the tourScheduleList
        restTourScheduleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDatetime").value(hasItem(DEFAULT_START_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].noPassengers").value(hasItem(DEFAULT_NO_PASSENGERS)))
            .andExpect(jsonPath("$.[*].noKids").value(hasItem(DEFAULT_NO_KIDS)))
            .andExpect(jsonPath("$.[*].noPets").value(hasItem(DEFAULT_NO_PETS)))
            .andExpect(jsonPath("$.[*].startPlace").value(hasItem(DEFAULT_START_PLACE)))
            .andExpect(jsonPath("$.[*].endPlace").value(hasItem(DEFAULT_END_PLACE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourSchedulesWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourScheduleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourScheduleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourScheduleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourSchedulesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourScheduleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourScheduleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourScheduleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTourSchedule() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        // Get the tourSchedule
        restTourScheduleMockMvc
            .perform(get(ENTITY_API_URL_ID, tourSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourSchedule.getId().intValue()))
            .andExpect(jsonPath("$.startDatetime").value(DEFAULT_START_DATETIME.toString()))
            .andExpect(jsonPath("$.noPassengers").value(DEFAULT_NO_PASSENGERS))
            .andExpect(jsonPath("$.noKids").value(DEFAULT_NO_KIDS))
            .andExpect(jsonPath("$.noPets").value(DEFAULT_NO_PETS))
            .andExpect(jsonPath("$.startPlace").value(DEFAULT_START_PLACE))
            .andExpect(jsonPath("$.endPlace").value(DEFAULT_END_PLACE));
    }

    @Test
    @Transactional
    void getNonExistingTourSchedule() throws Exception {
        // Get the tourSchedule
        restTourScheduleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTourSchedule() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();

        // Update the tourSchedule
        TourSchedule updatedTourSchedule = tourScheduleRepository.findById(tourSchedule.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTourSchedule are not directly saved in db
        em.detach(updatedTourSchedule);
        updatedTourSchedule
            .startDatetime(UPDATED_START_DATETIME)
            .noPassengers(UPDATED_NO_PASSENGERS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .startPlace(UPDATED_START_PLACE)
            .endPlace(UPDATED_END_PLACE);

        restTourScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTourSchedule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTourSchedule))
            )
            .andExpect(status().isOk());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
        TourSchedule testTourSchedule = tourScheduleList.get(tourScheduleList.size() - 1);
        assertThat(testTourSchedule.getStartDatetime()).isEqualTo(UPDATED_START_DATETIME);
        assertThat(testTourSchedule.getNoPassengers()).isEqualTo(UPDATED_NO_PASSENGERS);
        assertThat(testTourSchedule.getNoKids()).isEqualTo(UPDATED_NO_KIDS);
        assertThat(testTourSchedule.getNoPets()).isEqualTo(UPDATED_NO_PETS);
        assertThat(testTourSchedule.getStartPlace()).isEqualTo(UPDATED_START_PLACE);
        assertThat(testTourSchedule.getEndPlace()).isEqualTo(UPDATED_END_PLACE);
    }

    @Test
    @Transactional
    void putNonExistingTourSchedule() throws Exception {
        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();
        tourSchedule.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourSchedule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tourSchedule))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTourSchedule() throws Exception {
        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();
        tourSchedule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tourSchedule))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTourSchedule() throws Exception {
        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();
        tourSchedule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourSchedule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourScheduleWithPatch() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();

        // Update the tourSchedule using partial update
        TourSchedule partialUpdatedTourSchedule = new TourSchedule();
        partialUpdatedTourSchedule.setId(tourSchedule.getId());

        partialUpdatedTourSchedule.noKids(UPDATED_NO_KIDS).endPlace(UPDATED_END_PLACE);

        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTourSchedule))
            )
            .andExpect(status().isOk());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
        TourSchedule testTourSchedule = tourScheduleList.get(tourScheduleList.size() - 1);
        assertThat(testTourSchedule.getStartDatetime()).isEqualTo(DEFAULT_START_DATETIME);
        assertThat(testTourSchedule.getNoPassengers()).isEqualTo(DEFAULT_NO_PASSENGERS);
        assertThat(testTourSchedule.getNoKids()).isEqualTo(UPDATED_NO_KIDS);
        assertThat(testTourSchedule.getNoPets()).isEqualTo(DEFAULT_NO_PETS);
        assertThat(testTourSchedule.getStartPlace()).isEqualTo(DEFAULT_START_PLACE);
        assertThat(testTourSchedule.getEndPlace()).isEqualTo(UPDATED_END_PLACE);
    }

    @Test
    @Transactional
    void fullUpdateTourScheduleWithPatch() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();

        // Update the tourSchedule using partial update
        TourSchedule partialUpdatedTourSchedule = new TourSchedule();
        partialUpdatedTourSchedule.setId(tourSchedule.getId());

        partialUpdatedTourSchedule
            .startDatetime(UPDATED_START_DATETIME)
            .noPassengers(UPDATED_NO_PASSENGERS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .startPlace(UPDATED_START_PLACE)
            .endPlace(UPDATED_END_PLACE);

        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTourSchedule))
            )
            .andExpect(status().isOk());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
        TourSchedule testTourSchedule = tourScheduleList.get(tourScheduleList.size() - 1);
        assertThat(testTourSchedule.getStartDatetime()).isEqualTo(UPDATED_START_DATETIME);
        assertThat(testTourSchedule.getNoPassengers()).isEqualTo(UPDATED_NO_PASSENGERS);
        assertThat(testTourSchedule.getNoKids()).isEqualTo(UPDATED_NO_KIDS);
        assertThat(testTourSchedule.getNoPets()).isEqualTo(UPDATED_NO_PETS);
        assertThat(testTourSchedule.getStartPlace()).isEqualTo(UPDATED_START_PLACE);
        assertThat(testTourSchedule.getEndPlace()).isEqualTo(UPDATED_END_PLACE);
    }

    @Test
    @Transactional
    void patchNonExistingTourSchedule() throws Exception {
        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();
        tourSchedule.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tourSchedule))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTourSchedule() throws Exception {
        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();
        tourSchedule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tourSchedule))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTourSchedule() throws Exception {
        int databaseSizeBeforeUpdate = tourScheduleRepository.findAll().size();
        tourSchedule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tourSchedule))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourSchedule in the database
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTourSchedule() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        int databaseSizeBeforeDelete = tourScheduleRepository.findAll().size();

        // Delete the tourSchedule
        restTourScheduleMockMvc
            .perform(delete(ENTITY_API_URL_ID, tourSchedule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TourSchedule> tourScheduleList = tourScheduleRepository.findAll();
        assertThat(tourScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
