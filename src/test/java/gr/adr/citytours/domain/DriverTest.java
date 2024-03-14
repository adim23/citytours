package gr.adr.citytours.domain;

import static gr.adr.citytours.domain.DriverTestSamples.*;
import static gr.adr.citytours.domain.TourScheduleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.citytours.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DriverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Driver.class);
        Driver driver1 = getDriverSample1();
        Driver driver2 = new Driver();
        assertThat(driver1).isNotEqualTo(driver2);

        driver2.setId(driver1.getId());
        assertThat(driver1).isEqualTo(driver2);

        driver2 = getDriverSample2();
        assertThat(driver1).isNotEqualTo(driver2);
    }

    @Test
    void tourScheduleTest() throws Exception {
        Driver driver = getDriverRandomSampleGenerator();
        TourSchedule tourScheduleBack = getTourScheduleRandomSampleGenerator();

        driver.addTourSchedule(tourScheduleBack);
        assertThat(driver.getTourSchedules()).containsOnly(tourScheduleBack);
        assertThat(tourScheduleBack.getDriver()).isEqualTo(driver);

        driver.removeTourSchedule(tourScheduleBack);
        assertThat(driver.getTourSchedules()).doesNotContain(tourScheduleBack);
        assertThat(tourScheduleBack.getDriver()).isNull();

        driver.tourSchedules(new HashSet<>(Set.of(tourScheduleBack)));
        assertThat(driver.getTourSchedules()).containsOnly(tourScheduleBack);
        assertThat(tourScheduleBack.getDriver()).isEqualTo(driver);

        driver.setTourSchedules(new HashSet<>());
        assertThat(driver.getTourSchedules()).doesNotContain(tourScheduleBack);
        assertThat(tourScheduleBack.getDriver()).isNull();
    }
}
