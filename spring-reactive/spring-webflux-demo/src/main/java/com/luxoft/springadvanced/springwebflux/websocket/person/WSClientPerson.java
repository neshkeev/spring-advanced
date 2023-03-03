package com.luxoft.springadvanced.springwebflux.websocket.person;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

/**
 * We need take in receive() see
 * <a href="https://stackoverflow.com/questions/49631757/how-to-ensure-reactive-stream-completes-with-spring-webflux-and-websockets">...</a>
 * Docs for WebSocketHandler
 * <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-websockethandler">...</a>
 */
public class WSClientPerson {
    
    public static void main(String[] args) {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
            URI.create("ws://localhost:18080/wsperson"),
            session -> {
                Mono<Void> output = session.send(Flux.just(1,2,3)
                    .delayElements(Duration.ofSeconds(3))
                    .map(Object::toString)
                    .map(session::textMessage)
                    .doOnComplete(()-> System.out.println("output completed"))
                );

                Mono<Void> input = session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .doOnNext(System.out::println)
                    .then();

                // We need either to subscribe to input and return output,
                // or return output combined with input.
                // For this aim, we can use:
                // 1) then() - waiting output to complete,
                //      then waiting input to complete:
                //            output.then(input);
                //      In this case we will start receiving results
                //      only after output completes.
                // 2) and() - waiting both to complete:
                //            output.and(input)
                //      Since input wouldn't complete until server completes,
                //      we may use .take(3) or .takeUntil() to make it complete
                // 3) Mono.first() - waiting either to complete:
                //            Mono.first(output, input);
                // 4) Mono.zip() can be used for the same (as recommended in docs)
                //            Mono.first(output, input);

                return Mono.firstWithSignal(input,
                        output.then(Mono.delay(Duration.ofSeconds(10))))
//                         finish as soon as output completes;
//                         however, we need some more time so that
//                         the last message could finish processing
                        .then();
            })
            .block();

    }
}
