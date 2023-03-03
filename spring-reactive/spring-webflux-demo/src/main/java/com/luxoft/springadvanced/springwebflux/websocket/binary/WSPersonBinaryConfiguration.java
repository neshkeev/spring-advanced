package com.luxoft.springadvanced.springwebflux.websocket.binary;

import com.luxoft.springadvanced.springwebflux.domain.Person;
import com.luxoft.springadvanced.springwebflux.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;

import java.util.Map;

@Configuration
public class WSPersonBinaryConfiguration {
    private final PersonRepository personRepository;

    @Autowired
    public WSPersonBinaryConfiguration(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Bean
    public HandlerMapping wsPersonBinary() {
        return new SimpleUrlHandlerMapping(
            Map.<String, WebSocketHandler>of(
                "/wsbinary", session -> {
                    // get stream of person ids and convert it to Long
                    Flux<Long> idFlux = session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .map(Long::valueOf);

                    // retrieve persons by ids
                    // use concatMap to avoid results reordering
                    Flux<Person> personFlux = idFlux
                        .concatMap(personRepository::findById);

                    // convert String representation of person
                    // to the WebSocket text message
                    Flux<WebSocketMessage> personMessages =
                        personFlux
                        .map(v -> session.binaryMessage(dataBufferFactory ->
                            dataBufferFactory.wrap(v.toString().getBytes())));

                    // send a message
                    return session.send(personMessages);
                }
            ), 2);
    }

}
