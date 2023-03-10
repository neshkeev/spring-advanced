package com.luxoft.springadvanced.springactuatorsimple.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SecondsSinceStartBean {
    private final Counter secondsSinceStart;

    public SecondsSinceStartBean(MeterRegistry registry) {
        secondsSinceStart = registry.counter("seconds_since_start", Tags.empty());
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    public void action() {
        secondsSinceStart.increment();
    }
}
