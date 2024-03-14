package gr.adr.citytours.domain;

import static gr.adr.citytours.domain.TourScheduleTestSamples.*;
import static gr.adr.citytours.domain.VehicleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.citytours.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VehicleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicle.class);
        Vehicle vehicle1 = getVehicleSample1();
        Vehicle vehicle2 = new Vehicle();
        assertThat(vehicle1).isNotEqualTo(vehicle2);

        vehicle2.setId(vehicle1.getId());
        assertThat(vehicle1).isEqualTo(vehicle2);

        vehicle2 = getVehicleSample2();
        assertThat(vehicle1).isNotEqualTo(vehicle2);
    }

    @Test
    void tourScheduleTest() throws Exception {
        Vehicle vehicle = getVehicleRandomSampleGenerator();
        TourSchedule tourScheduleBack = getTourScheduleRandomSampleGenerator();

        vehicle.addTourSchedule(tourScheduleBack);
        assertThat(vehicle.getTourSchedules()).containsOnly(tourScheduleBack);
        assertThat(tourScheduleBack.getVehicle()).isEqualTo(vehicle);

        vehicle.removeTourSchedule(tourScheduleBack);
        assertThat(vehicle.getTourSchedules()).doesNotContain(tourScheduleBack);
        assertThat(tourScheduleBack.getVehicle()).isNull();

        vehicle.tourSchedules(new HashSet<>(Set.of(tourScheduleBack)));
        assertThat(vehicle.getTourSchedules()).containsOnly(tourScheduleBack);
        assertThat(tourScheduleBack.getVehicle()).isEqualTo(vehicle);

        vehicle.setTourSchedules(new HashSet<>());
        assertThat(vehicle.getTourSchedules()).doesNotContain(tourScheduleBack);
        assertThat(tourScheduleBack.getVehicle()).isNull();
    }
}
