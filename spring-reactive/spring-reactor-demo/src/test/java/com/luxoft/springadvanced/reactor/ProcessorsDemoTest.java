package com.luxoft.springadvanced.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("deprecation")
public class ProcessorsDemoTest {

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDirectProcessor() {
        // DirectProcessor can have multiple consumers,
        // and supports multiple producers.
        // However, all producers must produce messages
        // on the same Thread.
        // In terms of interaction model, DirectProcessor
        // only supports PUSH from the
        // source through the processor to the Subscribers
        //DirectProcessor<Long> data = DirectProcessor.create();
        // replace by EmitterProcessor which is honoring backpressure
        Sinks.many().multicast().onBackpressureBuffer();
        EmitterProcessor<Long> data = EmitterProcessor.create();
        data
                .publishOn(Schedulers.parallel())
                .subscribe(t -> {
                    sleep(1);
                    System.out.println(t);
                },
                        Throwable::printStackTrace,
                () -> System.out.println("Finished 1"));

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(()-> {
            for (long i=0; i<1000000; i++) data.onNext(i);
        });
        executorService.submit(()-> {
            for (long i=0; i<1000000; i++) data.onNext(i);
        });

        sleep(10000);
    }

    @Test
    public void testProcessorComplete() {
        // Once the Direct (or Emitter) Processor has terminated
        // (usually through its sink’s error(Throwable) or
        // complete() methods being called), it lets more
        // subscribers subscribe but replays
        // the termination signal to them immediately.
        DirectProcessor<Long> data = DirectProcessor.create();
        data.subscribe(System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Finished 1"));
        data.onNext(10L);
        //data.onComplete();
        data.subscribe(System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Finished 2"));

        data.onNext(12L);
        data.onComplete();
    }

    @Test
    public void testReplayProcessor() {
        ReplayProcessor<Integer> data = ReplayProcessor.create(3);
        data.subscribe(t -> System.out.println("subscriber1: "+t));
        data.onNext(666);
        FluxSink<Integer> sink = data.sink();
        sink.next(1);
        sink.next(2);
        sink.next(3);
        sink.next(4);
        sink.next(5);
        data.subscribe(t -> System.out.println("subscriber2: "+t));
    }

    @Test
    public void testReplayProcessorCacheLast() {
        ReplayProcessor<Integer> data = ReplayProcessor.cacheLast();
        data.subscribe(t -> System.out.println("subscriber1: "+t));
        FluxSink<Integer> sink = data.sink();
        sink.next(1);
        sink.next(2);
        sink.next(3);
        sink.next(4);
        sink.next(5);
        data.subscribe(t -> System.out.println("subscriber2: "+t));
    }
}
