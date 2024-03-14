package gr.adr.citytours.domain;

import static gr.adr.citytours.domain.CityTestSamples.*;
import static gr.adr.citytours.domain.TourScheduleTestSamples.*;
import static gr.adr.citytours.domain.TourStepTestSamples.*;
import static gr.adr.citytours.domain.TourTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.citytours.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TourTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tour.class);
        Tour tour1 = getTourSample1();
        Tour tour2 = new Tour();
        assertThat(tour1).isNotEqualTo(tour2);

        tour2.setId(tour1.getId());
        assertThat(tour1).isEqualTo(tour2);

        tour2 = getTourSample2();
        assertThat(tour1).isNotEqualTo(tour2);
    }

    @Test
    void tourScheduleTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        TourSchedule tourScheduleBack = getTourScheduleRandomSampleGenerator();

        tour.addTourSchedule(tourScheduleBack);
        assertThat(tour.getTourSchedules()).containsOnly(tourScheduleBack);
        assertThat(tourScheduleBack.getTour()).isEqualTo(tour);

        tour.removeTourSchedule(tourScheduleBack);
        assertThat(tour.getTourSchedules()).doesNotContain(tourScheduleBack);
        assertThat(tourScheduleBack.getTour()).isNull();

        tour.tourSchedules(new HashSet<>(Set.of(tourScheduleBack)));
        assertThat(tour.getTourSchedules()).containsOnly(tourScheduleBack);
        assertThat(tourScheduleBack.getTour()).isEqualTo(tour);

        tour.setTourSchedules(new HashSet<>());
        assertThat(tour.getTourSchedules()).doesNotContain(tourScheduleBack);
        assertThat(tourScheduleBack.getTour()).isNull();
    }

    @Test
    void stepsTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        TourStep tourStepBack = getTourStepRandomSampleGenerator();

        tour.addSteps(tourStepBack);
        assertThat(tour.getSteps()).containsOnly(tourStepBack);
        assertThat(tourStepBack.getTour()).isEqualTo(tour);

        tour.removeSteps(tourStepBack);
        assertThat(tour.getSteps()).doesNotContain(tourStepBack);
        assertThat(tourStepBack.getTour()).isNull();

        tour.steps(new HashSet<>(Set.of(tourStepBack)));
        assertThat(tour.getSteps()).containsOnly(tourStepBack);
        assertThat(tourStepBack.getTour()).isEqualTo(tour);

        tour.setSteps(new HashSet<>());
        assertThat(tour.getSteps()).doesNotContain(tourStepBack);
        assertThat(tourStepBack.getTour()).isNull();
    }

    @Test
    void cityTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        City cityBack = getCityRandomSampleGenerator();

        tour.setCity(cityBack);
        assertThat(tour.getCity()).isEqualTo(cityBack);

        tour.city(null);
        assertThat(tour.getCity()).isNull();
    }
}
