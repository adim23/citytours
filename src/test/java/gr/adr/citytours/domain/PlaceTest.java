package gr.adr.citytours.domain;

import static gr.adr.citytours.domain.CityTestSamples.*;
import static gr.adr.citytours.domain.ImageFileTestSamples.*;
import static gr.adr.citytours.domain.PlaceTestSamples.*;
import static gr.adr.citytours.domain.TourStepTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.citytours.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlaceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Place.class);
        Place place1 = getPlaceSample1();
        Place place2 = new Place();
        assertThat(place1).isNotEqualTo(place2);

        place2.setId(place1.getId());
        assertThat(place1).isEqualTo(place2);

        place2 = getPlaceSample2();
        assertThat(place1).isNotEqualTo(place2);
    }

    @Test
    void stepsTest() throws Exception {
        Place place = getPlaceRandomSampleGenerator();
        TourStep tourStepBack = getTourStepRandomSampleGenerator();

        place.addSteps(tourStepBack);
        assertThat(place.getSteps()).containsOnly(tourStepBack);
        assertThat(tourStepBack.getPlace()).isEqualTo(place);

        place.removeSteps(tourStepBack);
        assertThat(place.getSteps()).doesNotContain(tourStepBack);
        assertThat(tourStepBack.getPlace()).isNull();

        place.steps(new HashSet<>(Set.of(tourStepBack)));
        assertThat(place.getSteps()).containsOnly(tourStepBack);
        assertThat(tourStepBack.getPlace()).isEqualTo(place);

        place.setSteps(new HashSet<>());
        assertThat(place.getSteps()).doesNotContain(tourStepBack);
        assertThat(tourStepBack.getPlace()).isNull();
    }

    @Test
    void imagesTest() throws Exception {
        Place place = getPlaceRandomSampleGenerator();
        ImageFile imageFileBack = getImageFileRandomSampleGenerator();

        place.addImages(imageFileBack);
        assertThat(place.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getPlace()).isEqualTo(place);

        place.removeImages(imageFileBack);
        assertThat(place.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getPlace()).isNull();

        place.images(new HashSet<>(Set.of(imageFileBack)));
        assertThat(place.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getPlace()).isEqualTo(place);

        place.setImages(new HashSet<>());
        assertThat(place.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getPlace()).isNull();
    }

    @Test
    void cityTest() throws Exception {
        Place place = getPlaceRandomSampleGenerator();
        City cityBack = getCityRandomSampleGenerator();

        place.setCity(cityBack);
        assertThat(place.getCity()).isEqualTo(cityBack);

        place.city(null);
        assertThat(place.getCity()).isNull();
    }
}
