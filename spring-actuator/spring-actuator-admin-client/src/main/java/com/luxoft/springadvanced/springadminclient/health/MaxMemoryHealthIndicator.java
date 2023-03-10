package com.luxoft.springadvanced.springadminclient.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Service;

@Service
public class MaxMemoryHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long neededMemory = 10 * 1024 * 1024L;
        boolean invalid = maxMemory < neededMemory;
        Status status = invalid ? Status.DOWN : Status.UP;
        return Health.status(status).build();
    }
}
