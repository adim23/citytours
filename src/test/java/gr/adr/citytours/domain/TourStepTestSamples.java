package gr.adr.citytours.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TourStepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TourStep getTourStepSample1() {
        return new TourStep().id(1L).code("code1").stepOrder(1).waitTime(1L).driveTime(1);
    }

    public static TourStep getTourStepSample2() {
        return new TourStep().id(2L).code("code2").stepOrder(2).waitTime(2L).driveTime(2);
    }

    public static TourStep getTourStepRandomSampleGenerator() {
        return new TourStep()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .stepOrder(intCount.incrementAndGet())
            .waitTime(longCount.incrementAndGet())
            .driveTime(intCount.incrementAndGet());
    }
}
