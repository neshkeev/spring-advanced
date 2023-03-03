package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ParallelFluxDemoTest {
    @Test
    public void test() {
        Flux.range(1, 10)
                .parallel()
                .runOn(Schedulers.parallel())
                .subscribe(i -> System.out.println(
                        Thread.currentThread().getName() +
                                " -> " + i));
        sleep();
    }

    @Test
    public void testSequential() {
        Flux.range(1, 10)
                .parallel()
                .runOn(Schedulers.parallel())
                .doOnNext(ignore -> System.out.println("Before sequential thread: " + Thread.currentThread().getName()))
                .sequential()
                .subscribe(i -> System.out.println("After sequential thread:" +
                        Thread.currentThread().getName() +
                                " -> " + i));
        sleep();
    }

    private static void sleep()  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
