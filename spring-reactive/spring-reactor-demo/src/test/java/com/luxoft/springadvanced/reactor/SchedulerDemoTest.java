package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulerDemoTest {

    @Test
    public void testPublishOn() {
        var parallel = Schedulers.newParallel("parallel");
        final var flux = Flux.range(1, 2)
                .doOnNext(ignore -> System.out.println(Thread.currentThread().getName()))
                .publishOn(parallel)
                .doOnNext(ignore -> System.out.println(Thread.currentThread().getName()));

        flux.subscribe();
    }

    @Test
    public void test() throws InterruptedException {
        var parallel = Schedulers.newParallel("parallel");
        final var flux = Flux.range(1, 2)
                .doOnNext(ignore -> System.out.println(Thread.currentThread().getName()))
                .subscribeOn(parallel)
                .doOnNext(ignore -> System.out.println(Thread.currentThread().getName()));

        flux.subscribe();
    }
}
