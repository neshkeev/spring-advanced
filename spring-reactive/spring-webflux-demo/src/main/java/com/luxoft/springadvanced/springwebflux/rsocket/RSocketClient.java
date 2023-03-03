package com.luxoft.springadvanced.springwebflux.rsocket;

import com.luxoft.springadvanced.springwebflux.domain.Person;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.core.publisher.Flux;

public class RSocketClient {

    public static void main(String[] args) throws InterruptedException {
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();

        RSocketRequester rsocketRequester = RSocketRequester.builder()
                //.dataMimeType(MediaType.APPLICATION_JSON)
                .rsocketStrategies(strategies)
                .tcp("localhost", 17000);

        System.out.println("=== Request-Response: Find by id");
        //request response
        rsocketRequester.route("findById")
                .data(1)
                .retrieveMono(Person.class)
                .subscribe(System.out::println);

        Thread.sleep(1000);

        System.out.println("=== Request Stream: retrieve all persons");
        // request stream
        rsocketRequester
                .route("all")
                .retrieveFlux(Person.class)
                .subscribe(System.out::println);

        Thread.sleep(1000);

        System.out.println("=== Channel: get persons by id");
        // channel
        rsocketRequester.route("byId")
                .data(Flux.just(3,2,1))
                .retrieveFlux(Person.class)
                .subscribe(System.out::println);

        Thread.sleep(1000);
    }
}
