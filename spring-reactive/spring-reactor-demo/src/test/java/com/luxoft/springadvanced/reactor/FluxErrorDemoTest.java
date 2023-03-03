package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Random;

public class FluxErrorDemoTest {

    @Test
    public void testFluxError() {
        final var flux = Flux
                .error(new RuntimeException("Something went wrong"))
                .doOnError(error -> System.err.println("The error message is: " + error.getMessage()));
        processErrorFlux(flux);
    }

    @Test
    public void testCreatePossibleErrorFlux() {
        Flux<Integer> flux = Flux.create(sink -> {
            int amount = new Random().nextInt(10);
            do {
                if (amount > 6) {
                    sink.error(new RuntimeException("oops! amount is " + amount));
                } else {
                    sink.next(amount);
                }
                amount = new Random().nextInt(10);
            } while (amount > 6);
        });
        processErrorFlux(flux);
    }

    @Test
    public void testErrorDoOnNext() {
        final var fluxRetry = Flux.range(0, 5)
                .doOnNext(i -> {
                    if (i % 2 == 1) {
                        throw new IllegalStateException(i + " is odd");
                    }
                })
                .retry(2);
        processErrorFlux(fluxRetry);
    }

    private static<T> void processErrorFlux(Flux<T> flux) {
        flux
                .subscribe(System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("Done"));
    }
}
