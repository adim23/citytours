package gr.adr.citytours.domain;

import static gr.adr.citytours.domain.PlaceTestSamples.*;
import static gr.adr.citytours.domain.TourStepTestSamples.*;
import static gr.adr.citytours.domain.TourTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.citytours.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TourStepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourStep.class);
        TourStep tourStep1 = getTourStepSample1();
        TourStep tourStep2 = new TourStep();
        assertThat(tourStep1).isNotEqualTo(tourStep2);

        tourStep2.setId(tourStep1.getId());
        assertThat(tourStep1).isEqualTo(tourStep2);

        tourStep2 = getTourStepSample2();
        assertThat(tourStep1).isNotEqualTo(tourStep2);
    }

    @Test
    void tourTest() throws Exception {
        TourStep tourStep = getTourStepRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        tourStep.setTour(tourBack);
        assertThat(tourStep.getTour()).isEqualTo(tourBack);

        tourStep.tour(null);
        assertThat(tourStep.getTour()).isNull();
    }

    @Test
    void placeTest() throws Exception {
        TourStep tourStep = getTourStepRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        tourStep.setPlace(placeBack);
        assertThat(tourStep.getPlace()).isEqualTo(placeBack);

        tourStep.place(null);
        assertThat(tourStep.getPlace()).isNull();
    }
}
