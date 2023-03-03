package com.luxoft.springadvanced.springwebflux.websocket.simple;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

public class WSClient {
    public static void main(String[] args) {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
            URI.create("ws://localhost:18080/event-emitter"),
            session -> session.send(
                Mono.just(
                        session.textMessage(
                        "hi from websocket client")))
                .thenMany(session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .map(el -> el+"!")
                        .map(session::textMessage)
                        .map(Mono::just)
                        .flatMap(session::send)
                        )
                .then())
            .take(Duration.ofSeconds(10))
            .block();

    }
}
