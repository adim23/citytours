package gr.adr.citytours.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ImageFileTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ImageFile getImageFileSample1() {
        return new ImageFile().id(1L).filename("filename1");
    }

    public static ImageFile getImageFileSample2() {
        return new ImageFile().id(2L).filename("filename2");
    }

    public static ImageFile getImageFileRandomSampleGenerator() {
        return new ImageFile().id(longCount.incrementAndGet()).filename(UUID.randomUUID().toString());
    }
}
