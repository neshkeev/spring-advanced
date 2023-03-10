package com.luxoft.springadvanced.springactuatorsimple.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HelloDownHealthIndicator implements ReactiveHealthIndicator {
    @Override
    public Mono<Health> health() {
        return check()
                .onErrorResume(e ->
                        Mono.just(Health.down()
                                .withException(e)
                                .build())
                );
    }

    public Mono<Health> check() {
        return Mono.error(new IllegalStateException("Unable to start hello"));
    }
}
