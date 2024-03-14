package gr.adr.citytours.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DriverTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Driver getDriverSample1() {
        return new Driver().id(1L).firstName("firstName1").lastName("lastName1").age(1).email("email1").mobile("mobile1");
    }

    public static Driver getDriverSample2() {
        return new Driver().id(2L).firstName("firstName2").lastName("lastName2").age(2).email("email2").mobile("mobile2");
    }

    public static Driver getDriverRandomSampleGenerator() {
        return new Driver()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .age(intCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .mobile(UUID.randomUUID().toString());
    }
}
