package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

public class FluxCreateDemoTest {
    @Test
    public void testCreateInterval() throws InterruptedException {
        final var cities = List.of(
                "Bucharest", "Krakow",
                "Moscow", "Sofia");

        Flux<String> locations2 = Flux.interval(Duration.ofSeconds(1))
                .map(Math::toIntExact)
                .map(cities::get);

        final var disposable = locations2.subscribeOn(Schedulers.single())
                .subscribe(
                        s -> System.out.println("Location: " + s),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("I'm completed"));
        Thread.sleep(2100);
        disposable.dispose();
        disposable.dispose();
    }

    @Test
    public void testCreateSynk() throws InterruptedException {
        Flux<String> locations = Flux.create(location -> {
            location.next("Bucharest");
            sleep();
            location.error(new RuntimeException("Canberra"));
            sleep();
            location.next("Krakow");
            sleep();
            location.next("New York");
            sleep();
            location.next("Sofia");
            location.complete();
        });

        var disposable = locations.subscribeOn(Schedulers.single())
                .subscribe(
                        s -> System.out.println("Location: " + s),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("I'm completed"));
        Thread.sleep(3100);
        disposable.dispose();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ignore) {
        }
    }
}
