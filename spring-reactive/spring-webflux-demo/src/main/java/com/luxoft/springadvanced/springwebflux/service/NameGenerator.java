package com.luxoft.springadvanced.springwebflux.service;

import com.luxoft.springadvanced.springwebflux.domain.Person;
import com.luxoft.springadvanced.springwebflux.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Random;

@Component
public class NameGenerator {
    @Autowired
    private PersonRepository personRepository;

    private static final String[] names = {
            "Vasya", "Dima", "Misha", "Sasha"
    };
    private static final Random random = new Random();

    public Flux<String> names() {
        return Flux.generate(fluxSink -> {
            String name = names[random.nextInt(names.length)];
            fluxSink.next(name);
        });
    }

    public Flux<Person> persons() {
        return names()
                .flatMap(s->personRepository.findByName(s)
                .defaultIfEmpty(new Person(s,"not found")));
    }


}