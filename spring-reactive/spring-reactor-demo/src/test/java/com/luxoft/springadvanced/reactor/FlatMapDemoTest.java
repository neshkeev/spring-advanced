package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FlatMapDemoTest {
    static long delay = 500;

    @BeforeEach
    public void beforeEach() {
        delay = 500;
    }

    public static Flux<String> remoteRequest(String s) {
        delay -= 100;
        return Flux.just(s)
                .map(e -> "(" + e + ")")
                .map(e -> e + " (" + delay + ")")
                .delayElements(Duration.ofMillis(delay));
    }

    @Test
    public void test() throws InterruptedException {
        var locations = Flux.just("Bucharest",
                "Krakow",
                "Moscow",
                "London",
                "Sofia");

        final var subscription = locations
                .flatMap(FlatMapDemoTest::remoteRequest)
                .subscribe(System.out::println);

        Thread.sleep(2000);
        subscription.dispose();
    }

    @Test
    public void testConcat() throws InterruptedException {
        var locations = Flux.just("Bucharest",
                "Krakow",
                "Moscow",
                "London",
                "Sofia");

        final var subscription = locations
                .concatMap(FlatMapDemoTest::remoteRequest)
                .subscribe(System.out::println);

        Thread.sleep(2000);
        subscription.dispose();
    }
}
