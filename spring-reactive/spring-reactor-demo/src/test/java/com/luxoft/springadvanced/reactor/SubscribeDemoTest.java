package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class SubscribeDemoTest {

    @Test
    public void test() {
        final var locations = Flux.just("Bucharest", "Krakow", "Moscow", "Florence", "Sofia");

        locations.doOnNext(s -> System.out.println("Name:" + s))
                .map(String::length)
                .doOnNext(s -> System.out.println("Length:" + s))
                .filter(l -> l >= 5)
                .doOnNext(s -> System.out.println(s + " is longer than 4"))
                .map(e -> "Length: " + e)
                .subscribe(System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("Done."));
    }
}
