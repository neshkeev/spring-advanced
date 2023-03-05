package com.luxoft.springadvanced.springwebflux.controller;

import com.luxoft.springadvanced.springwebflux.domain.Person;
import com.luxoft.springadvanced.springwebflux.repo.PersonRepository;
import com.luxoft.springadvanced.springwebflux.service.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonRepository personRepository;
    private final NameGenerator nameGenerator;

    @Autowired
    public PersonController(PersonRepository personRepository,
                            NameGenerator nameGenerator) {
        this.personRepository = personRepository;
        this.nameGenerator = nameGenerator;
    }

    @SuppressWarnings("deprecation")
    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Person> list(@RequestParam(defaultValue = "0") Long start,
                             @RequestParam(defaultValue = "5") Long count) {
        return personRepository.findAll()
                .delayElements(Duration.ofSeconds(1))
                .skip(start)
                .take(count);
    }

    @SuppressWarnings("deprecation")
    // find person for each name
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<List<String>> getAll() {
        return getStream()
                .flatMap(s->personRepository.findByName(s)
                        .defaultIfEmpty(new Person(s,"not found")))
                .map(Person::toString)
                .buffer(2);
    }

    @GetMapping(path = "/stream")
    public Flux<String> getStream() {
        return Flux
                .just("Sasha","Misha","Dima", "Vasya")
                .delayElements(Duration.ofSeconds(2));
    }


    @GetMapping("/names")
    public Flux<String> names(@RequestParam(defaultValue = "0") Long start,
                              @RequestParam(defaultValue = "3") Long count) {
        return personRepository
                .findAll()
                .skip(start)
                .map(p->p.getName()+" ");
    }

    @GetMapping(path = "/persons", produces = "text/event-stream")
    public Flux<Person> persons() {
        return nameGenerator.persons()
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofSeconds(10));
    }

    @GetMapping(path = "/stats")
    public Mono<Map<String, Long>> stats() {
        return nameGenerator
                .persons()
                .take(100)
                .groupBy(Person::getName)
                .flatMap(group -> Mono.zip(
                        Mono.just(group.key()),
                        group.count()))
                .collectMap(Tuple2::getT1, Tuple2::getT2);
    }

    @PostMapping
    public Mono<Person> add(@RequestBody Person person) {
        return personRepository.save(person);
    }
}
