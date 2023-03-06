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

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class RSocketLBClient {
    public static final String MIME_ROUTER = "message/x.rsocket.composite-metadata.v0";

    public static void main(String[] args) {
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();

        Function<Integer, RSocket> getConnector = port -> RSocketConnector
                .create()
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .metadataMimeType(MIME_ROUTER)
                .connect(TcpClientTransport.create(port))
                .doOnSubscribe(s -> System.out.println("RSocket connection established on port " + port))
                .block();

        @SuppressWarnings("deprecation")
        List<RSocketSupplier> socketSuppliers = Flux.just(17000, 7001)
            .map(port -> new RSocketSupplier(
                ()->Mono.just(getConnector.apply(port))))
            .collectList().block();

        assert socketSuppliers != null;
        @SuppressWarnings("deprecation")
        LoadBalancedRSocketMono balancer = LoadBalancedRSocketMono
                    .create(Flux.just(socketSuppliers));

        AtomicInteger id = new AtomicInteger(1);
        Flux.range(1,4)
                .flatMap(i -> balancer)
                .map(rSocket ->
                    RSocketRequester.wrap(rSocket,
                        MimeTypeUtils.APPLICATION_JSON,
                        MimeType.valueOf(MIME_ROUTER),
                        strategies))
                .doOnNext(rSocket -> rSocket
                        .route("findById2")
                        .data(id.getAndIncrement())
                        .retrieveMono(Person.class)
                        .doOnNext(System.out::println)
                        .block()
                        )
                .blockLast();

    }
}
