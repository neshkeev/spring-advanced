package com.luxoft.springadvanced.springwebflux.websocket.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class WSPersonConfiguration {
    private final PersonRepository personRepository;
    private static final ObjectMapper converter = new ObjectMapper();

    @Autowired
    public WSPersonConfiguration(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Bean
    public HandlerMapping wsPerson() {
        return new SimpleUrlHandlerMapping(
            Map.<String, WebSocketHandler>of(
                "/wsperson", session -> {
                    // get stream of person ids and convert it to Long
                    Flux<Long> idFlux = session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .map(Long::valueOf);

                    // retrieve persons by ids
                    // use concatMap to avoid results reordering
                    Flux<Person> personFlux = idFlux
                        .concatMap(personRepository::findById);

                    // convert person to JSON String
                    Flux<String> personStringFlux = personFlux
                        .map(p -> {
                            try {
                                return converter.writeValueAsString(p);
                            }
                            catch (JsonProcessingException e) {
                                return "{\"error\":\"cannot convert to JSON\"}";
                            }
                        });

                    // convert String representation of person
                    // to the WebSocket text message
                    Flux<WebSocketMessage> personMessages = personStringFlux
                            .map(session::textMessage);

                    // send a message
                    return session.send(personMessages);
                }
            ), 1);
    }

}
