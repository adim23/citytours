package gr.adr.citytours.domain;

import static gr.adr.citytours.domain.ImageFileTestSamples.*;
import static gr.adr.citytours.domain.PlaceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.citytours.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImageFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageFile.class);
        ImageFile imageFile1 = getImageFileSample1();
        ImageFile imageFile2 = new ImageFile();
        assertThat(imageFile1).isNotEqualTo(imageFile2);

        imageFile2.setId(imageFile1.getId());
        assertThat(imageFile1).isEqualTo(imageFile2);

        imageFile2 = getImageFileSample2();
        assertThat(imageFile1).isNotEqualTo(imageFile2);
    }

    @Test
    void placeTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        imageFile.setPlace(placeBack);
        assertThat(imageFile.getPlace()).isEqualTo(placeBack);

        imageFile.place(null);
        assertThat(imageFile.getPlace()).isNull();
    }
}
