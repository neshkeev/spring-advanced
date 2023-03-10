package com.luxoft.springadvanced.springactuatorsimple.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Endpoint(id = "hello")
public class HelloEndpoint {

    @ReadOperation
    public Mono<Data> get() {
        return Mono.just(new Data("Hello", "World"));
    }

    @WriteOperation
    public void add(String name, int id) {
        System.out.println(id + "\t" + name);
    }

    @DeleteOperation
    public void delete() {
    }

    public record Data(String greeting, String who){}
}
