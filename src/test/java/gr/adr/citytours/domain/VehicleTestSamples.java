package gr.adr.citytours.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class VehicleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Vehicle getVehicleSample1() {
        return new Vehicle().id(1L).plate("plate1").type("type1").capacity(1).color("color1");
    }

    public static Vehicle getVehicleSample2() {
        return new Vehicle().id(2L).plate("plate2").type("type2").capacity(2).color("color2");
    }

    public static Vehicle getVehicleRandomSampleGenerator() {
        return new Vehicle()
            .id(longCount.incrementAndGet())
            .plate(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .capacity(intCount.incrementAndGet())
            .color(UUID.randomUUID().toString());
    }
}
