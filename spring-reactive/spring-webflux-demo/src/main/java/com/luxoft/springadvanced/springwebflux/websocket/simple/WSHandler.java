package com.luxoft.springadvanced.springwebflux.websocket.simple;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SuppressWarnings("NullableProblems")
@Component("WSHandler")
public class WSHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                Flux.interval(Duration.ofSeconds(1))
                    .map(v -> "next val: "+v)
                    .map(session::textMessage)
                ).and(
                    session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .doOnNext(System.out::println)
                    .log()
                );
    }
}
