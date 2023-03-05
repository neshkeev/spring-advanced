package com.luxoft.springadvanced.springwebflux.websocket.binary;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

/**
 * We need take in receive()
 * <a href="https://stackoverflow.com/questions/49631757/how-to-ensure-reactive-stream-completes-with-spring-webflux-and-websockets">see StackOverflow</a>
 * Docs for
 * <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-websockethandler">WebSocketHandler</a>
 */
public class WSClientPerson {
    
    public static void main(String[] args) {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
            URI.create("ws://localhost:18080/wsbinary"),
            session -> {
                Mono<Void> output = session.send(Flux.just(1,2,3)
                    .delayElements(Duration.ofSeconds(3))
                    .map(Object::toString)
                    .map(session::textMessage)
                    .doOnComplete(()-> System.out.println("output completed"))
                );

                Mono<Void> input = session.receive()
                        .map(WebSocketMessage::getPayload)
                        .map(dataBuffer -> {
                            var buffer = dataBuffer.toByteBuffer();
                            var bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            return new String(bytes);
                        })
                        .doOnNext(System.out::println)
                        .log()
                        .then();

                return Mono.firstWithSignal(
                        output.then(Mono.delay(Duration.ofSeconds(1))),
                        input)
                    .then();
            })
            .block();

    }
}
