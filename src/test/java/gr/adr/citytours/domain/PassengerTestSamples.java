package gr.adr.citytours.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PassengerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Passenger getPassengerSample1() {
        return new Passenger().id(1L).name("name1").email("email1").mobile("mobile1").age(1).gender("gender1").nationality("nationality1");
    }

    public static Passenger getPassengerSample2() {
        return new Passenger().id(2L).name("name2").email("email2").mobile("mobile2").age(2).gender("gender2").nationality("nationality2");
    }

    public static Passenger getPassengerRandomSampleGenerator() {
        return new Passenger()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .mobile(UUID.randomUUID().toString())
            .age(intCount.incrementAndGet())
            .gender(UUID.randomUUID().toString())
            .nationality(UUID.randomUUID().toString());
    }
}
