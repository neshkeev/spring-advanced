package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class SimplestDemoTest {

    @Test
    public void test() {
        final var cities = List.of("Moscow",
                "St. Petersburg",
                "Kazan",
                "Sochy",
                "Krasnoyarsk",
                "Khabarovsk");

        final var steps = Flux.interval(Duration.ofSeconds(1))
                .map(Math::toIntExact)
                .map(cities::get)
                .take(5);

        steps.subscribe(System.out::println);
        steps.blockLast();
    }
}
