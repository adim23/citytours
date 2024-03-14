package gr.adr.citytours.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BookingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Booking getBookingSample1() {
        return new Booking().id(1L).noPersons(1).noKids(1).noPets(1).paymentType("paymentType1").remoteId("remoteId1");
    }

    public static Booking getBookingSample2() {
        return new Booking().id(2L).noPersons(2).noKids(2).noPets(2).paymentType("paymentType2").remoteId("remoteId2");
    }

    public static Booking getBookingRandomSampleGenerator() {
        return new Booking()
            .id(longCount.incrementAndGet())
            .noPersons(intCount.incrementAndGet())
            .noKids(intCount.incrementAndGet())
            .noPets(intCount.incrementAndGet())
            .paymentType(UUID.randomUUID().toString())
            .remoteId(UUID.randomUUID().toString());
    }
}
