package gr.adr.citytours.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TourTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Tour getTourSample1() {
        return new Tour().id(1L).code("code1").title("title1").duration(1);
    }

    public static Tour getTourSample2() {
        return new Tour().id(2L).code("code2").title("title2").duration(2);
    }

    public static Tour getTourRandomSampleGenerator() {
        return new Tour()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .duration(intCount.incrementAndGet());
    }
}
