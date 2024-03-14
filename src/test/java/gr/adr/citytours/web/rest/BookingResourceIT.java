package gr.adr.citytours.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gr.adr.citytours.IntegrationTest;
import gr.adr.citytours.domain.Booking;
import gr.adr.citytours.repository.BookingRepository;
import gr.adr.citytours.service.BookingService;
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
 * Integration tests for the {@link BookingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BookingResourceIT {

    private static final Instant DEFAULT_BOOK_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOK_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NO_PERSONS = 1;
    private static final Integer UPDATED_NO_PERSONS = 2;

    private static final Integer DEFAULT_NO_KIDS = 1;
    private static final Integer UPDATED_NO_KIDS = 2;

    private static final Integer DEFAULT_NO_PETS = 1;
    private static final Integer UPDATED_NO_PETS = 2;

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;

    private static final String DEFAULT_PAYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final Instant DEFAULT_CANCELLED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CANCELLED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMOTE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTE_ID = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bookings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookingRepository bookingRepository;

    @Mock
    private BookingRepository bookingRepositoryMock;

    @Mock
    private BookingService bookingServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingMockMvc;

    private Booking booking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createEntity(EntityManager em) {
        Booking booking = new Booking()
            .bookDatetime(DEFAULT_BOOK_DATETIME)
            .noPersons(DEFAULT_NO_PERSONS)
            .noKids(DEFAULT_NO_KIDS)
            .noPets(DEFAULT_NO_PETS)
            .cost(DEFAULT_COST)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .valid(DEFAULT_VALID)
            .cancelledAt(DEFAULT_CANCELLED_AT)
            .remoteData(DEFAULT_REMOTE_DATA)
            .remoteId(DEFAULT_REMOTE_ID);
        return booking;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createUpdatedEntity(EntityManager em) {
        Booking booking = new Booking()
            .bookDatetime(UPDATED_BOOK_DATETIME)
            .noPersons(UPDATED_NO_PERSONS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .cost(UPDATED_COST)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .valid(UPDATED_VALID)
            .cancelledAt(UPDATED_CANCELLED_AT)
            .remoteData(UPDATED_REMOTE_DATA)
            .remoteId(UPDATED_REMOTE_ID);
        return booking;
    }

    @BeforeEach
    public void initTest() {
        booking = createEntity(em);
    }

    @Test
    @Transactional
    void createBooking() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();
        // Create the Booking
        restBookingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isCreated());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate + 1);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getBookDatetime()).isEqualTo(DEFAULT_BOOK_DATETIME);
        assertThat(testBooking.getNoPersons()).isEqualTo(DEFAULT_NO_PERSONS);
        assertThat(testBooking.getNoKids()).isEqualTo(DEFAULT_NO_KIDS);
        assertThat(testBooking.getNoPets()).isEqualTo(DEFAULT_NO_PETS);
        assertThat(testBooking.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testBooking.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testBooking.getValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testBooking.getCancelledAt()).isEqualTo(DEFAULT_CANCELLED_AT);
        assertThat(testBooking.getRemoteData()).isEqualTo(DEFAULT_REMOTE_DATA);
        assertThat(testBooking.getRemoteId()).isEqualTo(DEFAULT_REMOTE_ID);
    }

    @Test
    @Transactional
    void createBookingWithExistingId() throws Exception {
        // Create the Booking with an existing ID
        booking.setId(1L);

        int databaseSizeBeforeCreate = bookingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBookDatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingRepository.findAll().size();
        // set the field null
        booking.setBookDatetime(null);

        // Create the Booking, which fails.

        restBookingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isBadRequest());

        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBookings() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList
        restBookingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(booking.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookDatetime").value(hasItem(DEFAULT_BOOK_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].noPersons").value(hasItem(DEFAULT_NO_PERSONS)))
            .andExpect(jsonPath("$.[*].noKids").value(hasItem(DEFAULT_NO_KIDS)))
            .andExpect(jsonPath("$.[*].noPets").value(hasItem(DEFAULT_NO_PETS)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].cancelledAt").value(hasItem(DEFAULT_CANCELLED_AT.toString())))
            .andExpect(jsonPath("$.[*].remoteData").value(hasItem(DEFAULT_REMOTE_DATA.toString())))
            .andExpect(jsonPath("$.[*].remoteId").value(hasItem(DEFAULT_REMOTE_ID)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBookingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(bookingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bookingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBookingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bookingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(bookingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get the booking
        restBookingMockMvc
            .perform(get(ENTITY_API_URL_ID, booking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(booking.getId().intValue()))
            .andExpect(jsonPath("$.bookDatetime").value(DEFAULT_BOOK_DATETIME.toString()))
            .andExpect(jsonPath("$.noPersons").value(DEFAULT_NO_PERSONS))
            .andExpect(jsonPath("$.noKids").value(DEFAULT_NO_KIDS))
            .andExpect(jsonPath("$.noPets").value(DEFAULT_NO_PETS))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()))
            .andExpect(jsonPath("$.cancelledAt").value(DEFAULT_CANCELLED_AT.toString()))
            .andExpect(jsonPath("$.remoteData").value(DEFAULT_REMOTE_DATA.toString()))
            .andExpect(jsonPath("$.remoteId").value(DEFAULT_REMOTE_ID));
    }

    @Test
    @Transactional
    void getNonExistingBooking() throws Exception {
        // Get the booking
        restBookingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking
        Booking updatedBooking = bookingRepository.findById(booking.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBooking are not directly saved in db
        em.detach(updatedBooking);
        updatedBooking
            .bookDatetime(UPDATED_BOOK_DATETIME)
            .noPersons(UPDATED_NO_PERSONS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .cost(UPDATED_COST)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .valid(UPDATED_VALID)
            .cancelledAt(UPDATED_CANCELLED_AT)
            .remoteData(UPDATED_REMOTE_DATA)
            .remoteId(UPDATED_REMOTE_ID);

        restBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBooking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBooking))
            )
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getBookDatetime()).isEqualTo(UPDATED_BOOK_DATETIME);
        assertThat(testBooking.getNoPersons()).isEqualTo(UPDATED_NO_PERSONS);
        assertThat(testBooking.getNoKids()).isEqualTo(UPDATED_NO_KIDS);
        assertThat(testBooking.getNoPets()).isEqualTo(UPDATED_NO_PETS);
        assertThat(testBooking.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testBooking.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testBooking.getValid()).isEqualTo(UPDATED_VALID);
        assertThat(testBooking.getCancelledAt()).isEqualTo(UPDATED_CANCELLED_AT);
        assertThat(testBooking.getRemoteData()).isEqualTo(UPDATED_REMOTE_DATA);
        assertThat(testBooking.getRemoteId()).isEqualTo(UPDATED_REMOTE_ID);
    }

    @Test
    @Transactional
    void putNonExistingBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, booking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(booking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(booking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookingWithPatch() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking using partial update
        Booking partialUpdatedBooking = new Booking();
        partialUpdatedBooking.setId(booking.getId());

        partialUpdatedBooking
            .bookDatetime(UPDATED_BOOK_DATETIME)
            .noPersons(UPDATED_NO_PERSONS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .valid(UPDATED_VALID);

        restBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBooking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBooking))
            )
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getBookDatetime()).isEqualTo(UPDATED_BOOK_DATETIME);
        assertThat(testBooking.getNoPersons()).isEqualTo(UPDATED_NO_PERSONS);
        assertThat(testBooking.getNoKids()).isEqualTo(UPDATED_NO_KIDS);
        assertThat(testBooking.getNoPets()).isEqualTo(UPDATED_NO_PETS);
        assertThat(testBooking.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testBooking.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testBooking.getValid()).isEqualTo(UPDATED_VALID);
        assertThat(testBooking.getCancelledAt()).isEqualTo(DEFAULT_CANCELLED_AT);
        assertThat(testBooking.getRemoteData()).isEqualTo(DEFAULT_REMOTE_DATA);
        assertThat(testBooking.getRemoteId()).isEqualTo(DEFAULT_REMOTE_ID);
    }

    @Test
    @Transactional
    void fullUpdateBookingWithPatch() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking using partial update
        Booking partialUpdatedBooking = new Booking();
        partialUpdatedBooking.setId(booking.getId());

        partialUpdatedBooking
            .bookDatetime(UPDATED_BOOK_DATETIME)
            .noPersons(UPDATED_NO_PERSONS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .cost(UPDATED_COST)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .valid(UPDATED_VALID)
            .cancelledAt(UPDATED_CANCELLED_AT)
            .remoteData(UPDATED_REMOTE_DATA)
            .remoteId(UPDATED_REMOTE_ID);

        restBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBooking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBooking))
            )
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getBookDatetime()).isEqualTo(UPDATED_BOOK_DATETIME);
        assertThat(testBooking.getNoPersons()).isEqualTo(UPDATED_NO_PERSONS);
        assertThat(testBooking.getNoKids()).isEqualTo(UPDATED_NO_KIDS);
        assertThat(testBooking.getNoPets()).isEqualTo(UPDATED_NO_PETS);
        assertThat(testBooking.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testBooking.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testBooking.getValid()).isEqualTo(UPDATED_VALID);
        assertThat(testBooking.getCancelledAt()).isEqualTo(UPDATED_CANCELLED_AT);
        assertThat(testBooking.getRemoteData()).isEqualTo(UPDATED_REMOTE_DATA);
        assertThat(testBooking.getRemoteId()).isEqualTo(UPDATED_REMOTE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, booking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(booking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(booking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        int databaseSizeBeforeDelete = bookingRepository.findAll().size();

        // Delete the booking
        restBookingMockMvc
            .perform(delete(ENTITY_API_URL_ID, booking.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
