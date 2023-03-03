package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BackpressureDemoTest {

    static {
        // buffer size for publishOn is 256 by default
        System.setProperty("reactor.bufferSize.small", "16");
    }

    @Test
    public void testBackpressure() {
        var parallel = Schedulers.parallel();
        final var last = Flux.range(1, 300)
                // backpressure by default - unbounded buffer
                .onBackpressureBuffer()
                .publishOn(parallel)
                .doOnNext(v -> System.out.println("Received " + v))
                .blockLast();
        System.out.println(last);
        parallel.dispose();
    }
    @Test
    public void testBackpressureBoundedBuffer() {
        var parallel = Schedulers.parallel();
        final var last = Flux.range(1, 300)
                // backpressure by default - unbounded buffer
                .onBackpressureBuffer(100, BufferOverflowStrategy.DROP_LATEST)
                .publishOn(parallel)
                .doOnNext(v -> System.out.println("Received " + v))
                .blockLast();
        System.out.println(last);
        parallel.dispose();
    }
    @Test
    public void testBackpressureDrop() {
        var parallel = Schedulers.parallel();
        final var last = Flux.range(1, 300)
                .onBackpressureDrop()
                .publishOn(parallel)
                .doOnNext(v -> System.out.println("Received " + v))
                .blockLast();
        System.out.println(last);
        parallel.dispose();
    }
    @Test
    public void testBackpressureDrop5() {
        var parallel = Schedulers.parallel();
        final var last = Flux.range(1, 300)
                .onBackpressureDrop()
                .publishOn(parallel, 5)
                .doOnNext(v -> System.out.println("Received " + v))
                .blockLast();
        System.out.println(last);
        parallel.dispose();
    }

    @Test
    public void testBackpressureLatest() {
        var parallel = Schedulers.parallel();
        final var last = Flux.range(1, 300)
                .onBackpressureLatest()
                .publishOn(parallel, 20)
                .doOnNext(v -> System.out.println("Received " + v))
                .blockLast();
        System.out.println(last);
        parallel.dispose();
    }

    @Test
    public void testBackpressureError() {
        var parallel = Schedulers.parallel();
        final var errorFlux = Flux.range(1, 300)
                .onBackpressureError()
                .publishOn(parallel)
                .doOnNext(v -> System.out.println("Received " + v));
        assertThrows(Exception.class, errorFlux::blockLast);
        parallel.dispose();
    }
}
