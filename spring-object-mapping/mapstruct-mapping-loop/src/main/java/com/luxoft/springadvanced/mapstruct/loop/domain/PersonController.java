package com.luxoft.springadvanced.mapstruct.loop.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/persons")
    public Flux<Person> getPersons() {
        return Flux.fromIterable(personRepository.findAll());
    }
}
