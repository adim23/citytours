package gr.adr.citytours.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TourScheduleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TourSchedule getTourScheduleSample1() {
        return new TourSchedule().id(1L).noPassengers(1).noKids(1).noPets(1).startPlace("startPlace1").endPlace("endPlace1");
    }

    public static TourSchedule getTourScheduleSample2() {
        return new TourSchedule().id(2L).noPassengers(2).noKids(2).noPets(2).startPlace("startPlace2").endPlace("endPlace2");
    }

    public static TourSchedule getTourScheduleRandomSampleGenerator() {
        return new TourSchedule()
            .id(longCount.incrementAndGet())
            .noPassengers(intCount.incrementAndGet())
            .noKids(intCount.incrementAndGet())
            .noPets(intCount.incrementAndGet())
            .startPlace(UUID.randomUUID().toString())
            .endPlace(UUID.randomUUID().toString());
    }
}
