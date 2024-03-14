package gr.adr.citytours.domain;

import static gr.adr.citytours.domain.BookingTestSamples.*;
import static gr.adr.citytours.domain.PassengerTestSamples.*;
import static gr.adr.citytours.domain.TourScheduleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.citytours.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Booking.class);
        Booking booking1 = getBookingSample1();
        Booking booking2 = new Booking();
        assertThat(booking1).isNotEqualTo(booking2);

        booking2.setId(booking1.getId());
        assertThat(booking1).isEqualTo(booking2);

        booking2 = getBookingSample2();
        assertThat(booking1).isNotEqualTo(booking2);
    }

    @Test
    void scheduleTest() throws Exception {
        Booking booking = getBookingRandomSampleGenerator();
        TourSchedule tourScheduleBack = getTourScheduleRandomSampleGenerator();

        booking.setSchedule(tourScheduleBack);
        assertThat(booking.getSchedule()).isEqualTo(tourScheduleBack);

        booking.schedule(null);
        assertThat(booking.getSchedule()).isNull();
    }

    @Test
    void passengerTest() throws Exception {
        Booking booking = getBookingRandomSampleGenerator();
        Passenger passengerBack = getPassengerRandomSampleGenerator();

        booking.setPassenger(passengerBack);
        assertThat(booking.getPassenger()).isEqualTo(passengerBack);

        booking.passenger(null);
        assertThat(booking.getPassenger()).isNull();
    }
}
