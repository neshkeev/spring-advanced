package com.luxoft.springadvanced.springwebflux.rsocket;

import com.luxoft.springadvanced.springwebflux.domain.Person;
import io.rsocket.RSocket;
import io.rsocket.client.LoadBalancedRSocketMono;
import io.rsocket.client.filter.RSocketSupplier;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;


public class RSocketLBStatistics {
    public static final String MIME_ROUTER = "message/x.rsocket.composite-metadata.v0";

    public static void main(String[] args) throws InterruptedException {
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();

        Function<Integer, Mono<RSocket>> getConnector = port ->
            Mono.from(RSocketConnector
                .create()
                .reconnect(Retry.fixedDelay(100, Duration.ofSeconds(1)))
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .metadataMimeType(MIME_ROUTER)
                .connect(TcpClientTransport.create(port))
                .doOnSubscribe(s -> System.out.println("RSocket connection established on port " + port))
            );

        @SuppressWarnings("deprecation")
        LoadBalancedRSocketMono balancer = LoadBalancedRSocketMono.create(
            Flux.just(17000, 7001)
            .map(port -> new RSocketSupplier(() -> getConnector.apply(port)))
            .collectList()
        );

        // we need to wait at least 1 RSocket in the balancer
        while (balancer.availability() == 0.0) {
            //noinspection BusyWait
            Thread.sleep(1);
        }

        AtomicInteger id = new AtomicInteger(1);
        Flux.range(1,5000)
                .flatMap(i -> balancer)
                .retry()
                .map(rSocket ->
                    RSocketRequester.wrap(rSocket,
                        MimeTypeUtils.APPLICATION_JSON,
                        MimeType.valueOf(MIME_ROUTER),
                        strategies))
                .flatMap(rSocket ->
                        rSocket
                        .route("findById2")
                        .data(id.getAndIncrement())
                        .retrieveMono(Person.class)
                        .retryWhen(Retry.fixedDelay(
                                100, Duration.ofSeconds(2)))
                )
                .doOnNext(System.out::println)
                .blockLast();
        System.out.println(balancer);
    }
}
