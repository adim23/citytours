package gr.adr.citytours.domain;

import static gr.adr.citytours.domain.CityTestSamples.*;
import static gr.adr.citytours.domain.PlaceTestSamples.*;
import static gr.adr.citytours.domain.TourTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.citytours.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(City.class);
        City city1 = getCitySample1();
        City city2 = new City();
        assertThat(city1).isNotEqualTo(city2);

        city2.setId(city1.getId());
        assertThat(city1).isEqualTo(city2);

        city2 = getCitySample2();
        assertThat(city1).isNotEqualTo(city2);
    }

    @Test
    void toursTest() throws Exception {
        City city = getCityRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        city.addTours(tourBack);
        assertThat(city.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getCity()).isEqualTo(city);

        city.removeTours(tourBack);
        assertThat(city.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getCity()).isNull();

        city.tours(new HashSet<>(Set.of(tourBack)));
        assertThat(city.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getCity()).isEqualTo(city);

        city.setTours(new HashSet<>());
        assertThat(city.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getCity()).isNull();
    }

    @Test
    void placesTest() throws Exception {
        City city = getCityRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        city.addPlaces(placeBack);
        assertThat(city.getPlaces()).containsOnly(placeBack);
        assertThat(placeBack.getCity()).isEqualTo(city);

        city.removePlaces(placeBack);
        assertThat(city.getPlaces()).doesNotContain(placeBack);
        assertThat(placeBack.getCity()).isNull();

        city.places(new HashSet<>(Set.of(placeBack)));
        assertThat(city.getPlaces()).containsOnly(placeBack);
        assertThat(placeBack.getCity()).isEqualTo(city);

        city.setPlaces(new HashSet<>());
        assertThat(city.getPlaces()).doesNotContain(placeBack);
        assertThat(placeBack.getCity()).isNull();
    }
}
