package com.luxoft.springadvanced.springwebflux.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RootHandler {
    public Mono<ServerResponse> root(ServerRequest ignore) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .bodyValue("<h1>hi</h1>");
    }

    public Mono<ServerResponse> hello(ServerRequest ignore) {
        Flux<String> data = Flux
                .just("Hello","From reactive","Spring","WebFlux","!")
                .delayElements(Duration.ofSeconds(1))
                .map(s->s+" ");

        return ServerResponse.ok().body(data, String.class);
    }
}
