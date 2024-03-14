package gr.adr.citytours.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gr.adr.citytours.IntegrationTest;
import gr.adr.citytours.domain.TourStep;
import gr.adr.citytours.repository.TourStepRepository;
import gr.adr.citytours.service.TourStepService;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link TourStepResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourStepResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STEP_ORDER = 1;
    private static final Integer UPDATED_STEP_ORDER = 2;

    private static final Long DEFAULT_WAIT_TIME = 1L;
    private static final Long UPDATED_WAIT_TIME = 2L;

    private static final Integer DEFAULT_DRIVE_TIME = 1;
    private static final Integer UPDATED_DRIVE_TIME = 2;

    private static final String ENTITY_API_URL = "/api/tour-steps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TourStepRepository tourStepRepository;

    @Mock
    private TourStepRepository tourStepRepositoryMock;

    @Mock
    private TourStepService tourStepServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourStepMockMvc;

    private TourStep tourStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourStep createEntity(EntityManager em) {
        TourStep tourStep = new TourStep()
            .code(DEFAULT_CODE)
            .stepOrder(DEFAULT_STEP_ORDER)
            .waitTime(DEFAULT_WAIT_TIME)
            .driveTime(DEFAULT_DRIVE_TIME);
        return tourStep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourStep createUpdatedEntity(EntityManager em) {
        TourStep tourStep = new TourStep()
            .code(UPDATED_CODE)
            .stepOrder(UPDATED_STEP_ORDER)
            .waitTime(UPDATED_WAIT_TIME)
            .driveTime(UPDATED_DRIVE_TIME);
        return tourStep;
    }

    @BeforeEach
    public void initTest() {
        tourStep = createEntity(em);
    }

    @Test
    @Transactional
    void createTourStep() throws Exception {
        int databaseSizeBeforeCreate = tourStepRepository.findAll().size();
        // Create the TourStep
        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourStep)))
            .andExpect(status().isCreated());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeCreate + 1);
        TourStep testTourStep = tourStepList.get(tourStepList.size() - 1);
        assertThat(testTourStep.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTourStep.getStepOrder()).isEqualTo(DEFAULT_STEP_ORDER);
        assertThat(testTourStep.getWaitTime()).isEqualTo(DEFAULT_WAIT_TIME);
        assertThat(testTourStep.getDriveTime()).isEqualTo(DEFAULT_DRIVE_TIME);
    }

    @Test
    @Transactional
    void createTourStepWithExistingId() throws Exception {
        // Create the TourStep with an existing ID
        tourStep.setId(1L);

        int databaseSizeBeforeCreate = tourStepRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourStep)))
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourStepRepository.findAll().size();
        // set the field null
        tourStep.setCode(null);

        // Create the TourStep, which fails.

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourStep)))
            .andExpect(status().isBadRequest());

        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStepOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourStepRepository.findAll().size();
        // set the field null
        tourStep.setStepOrder(null);

        // Create the TourStep, which fails.

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourStep)))
            .andExpect(status().isBadRequest());

        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTourSteps() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        // Get all the tourStepList
        restTourStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].stepOrder").value(hasItem(DEFAULT_STEP_ORDER)))
            .andExpect(jsonPath("$.[*].waitTime").value(hasItem(DEFAULT_WAIT_TIME.intValue())))
            .andExpect(jsonPath("$.[*].driveTime").value(hasItem(DEFAULT_DRIVE_TIME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourStepsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourStepServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourStepMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourStepServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourStepsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourStepServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourStepMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourStepRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTourStep() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        // Get the tourStep
        restTourStepMockMvc
            .perform(get(ENTITY_API_URL_ID, tourStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourStep.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.stepOrder").value(DEFAULT_STEP_ORDER))
            .andExpect(jsonPath("$.waitTime").value(DEFAULT_WAIT_TIME.intValue()))
            .andExpect(jsonPath("$.driveTime").value(DEFAULT_DRIVE_TIME));
    }

    @Test
    @Transactional
    void getNonExistingTourStep() throws Exception {
        // Get the tourStep
        restTourStepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTourStep() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();

        // Update the tourStep
        TourStep updatedTourStep = tourStepRepository.findById(tourStep.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTourStep are not directly saved in db
        em.detach(updatedTourStep);
        updatedTourStep.code(UPDATED_CODE).stepOrder(UPDATED_STEP_ORDER).waitTime(UPDATED_WAIT_TIME).driveTime(UPDATED_DRIVE_TIME);

        restTourStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTourStep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTourStep))
            )
            .andExpect(status().isOk());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
        TourStep testTourStep = tourStepList.get(tourStepList.size() - 1);
        assertThat(testTourStep.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTourStep.getStepOrder()).isEqualTo(UPDATED_STEP_ORDER);
        assertThat(testTourStep.getWaitTime()).isEqualTo(UPDATED_WAIT_TIME);
        assertThat(testTourStep.getDriveTime()).isEqualTo(UPDATED_DRIVE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingTourStep() throws Exception {
        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();
        tourStep.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourStep.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tourStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTourStep() throws Exception {
        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();
        tourStep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tourStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTourStep() throws Exception {
        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();
        tourStep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tourStep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourStepWithPatch() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();

        // Update the tourStep using partial update
        TourStep partialUpdatedTourStep = new TourStep();
        partialUpdatedTourStep.setId(tourStep.getId());

        partialUpdatedTourStep.waitTime(UPDATED_WAIT_TIME).driveTime(UPDATED_DRIVE_TIME);

        restTourStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTourStep))
            )
            .andExpect(status().isOk());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
        TourStep testTourStep = tourStepList.get(tourStepList.size() - 1);
        assertThat(testTourStep.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTourStep.getStepOrder()).isEqualTo(DEFAULT_STEP_ORDER);
        assertThat(testTourStep.getWaitTime()).isEqualTo(UPDATED_WAIT_TIME);
        assertThat(testTourStep.getDriveTime()).isEqualTo(UPDATED_DRIVE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateTourStepWithPatch() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();

        // Update the tourStep using partial update
        TourStep partialUpdatedTourStep = new TourStep();
        partialUpdatedTourStep.setId(tourStep.getId());

        partialUpdatedTourStep.code(UPDATED_CODE).stepOrder(UPDATED_STEP_ORDER).waitTime(UPDATED_WAIT_TIME).driveTime(UPDATED_DRIVE_TIME);

        restTourStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTourStep))
            )
            .andExpect(status().isOk());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
        TourStep testTourStep = tourStepList.get(tourStepList.size() - 1);
        assertThat(testTourStep.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTourStep.getStepOrder()).isEqualTo(UPDATED_STEP_ORDER);
        assertThat(testTourStep.getWaitTime()).isEqualTo(UPDATED_WAIT_TIME);
        assertThat(testTourStep.getDriveTime()).isEqualTo(UPDATED_DRIVE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingTourStep() throws Exception {
        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();
        tourStep.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tourStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTourStep() throws Exception {
        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();
        tourStep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tourStep))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTourStep() throws Exception {
        int databaseSizeBeforeUpdate = tourStepRepository.findAll().size();
        tourStep.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tourStep)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourStep in the database
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTourStep() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        int databaseSizeBeforeDelete = tourStepRepository.findAll().size();

        // Delete the tourStep
        restTourStepMockMvc
            .perform(delete(ENTITY_API_URL_ID, tourStep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TourStep> tourStepList = tourStepRepository.findAll();
        assertThat(tourStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
