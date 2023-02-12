package com.luxoft.springadvanced.beanvalidation.domain;

import com.luxoft.springadvanced.beanvalidation.error.PersonNotFoundException;
import com.luxoft.springadvanced.beanvalidation.error.PersonUnsupportedFieldPatchException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@Validated
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/persons")
    public Flux<Person> findAll() {
        final List<Person> all = repository.findAll();
        return Flux.fromIterable(all);
    }

    @PostMapping("/persons")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> newPerson(@Valid @RequestBody Person newPerson) {
        final var saved = repository.save(newPerson);
        return Mono.just(saved);
    }

    @GetMapping("/persons/{id}")
    public Mono<Person> findOne(@Valid @PathVariable @Min(1) Long id) {
        return repository.findById(id)
                .map(Mono::just)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @PutMapping("/persons/{id}")
    public Mono<Person> saveOrUpdate(@Valid @RequestBody Person newPerson, @PathVariable Long id) {
        final Person data = repository.findById(id)
                .map(person -> {
                    person.setName(newPerson.getName());
                    person.setCountry(newPerson.getCountry());
                    person.setSalary(newPerson.getSalary());
                    return person;
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return newPerson;
                });

        return Mono.just(repository.save(data));
    }

    @PatchMapping("/persons/{id}")
    public Mono<Person> patch(@RequestBody Map<String, String> update, @PathVariable Long id) {
        return repository.findById(id)
                .map(person -> {
                    if (update.size() != 1) {
                        throw new PersonUnsupportedFieldPatchException(update.keySet());
                    }

                    final String country = update.get("country");
                    person.setCountry(country);
                    return repository.save(person);
                })
                .map(Mono::just)
                .orElseThrow(() -> new PersonNotFoundException(id));

    }

    @DeleteMapping("/persons/{id}")
    public Mono<Void> deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
        return Mono.empty();
    }
}
