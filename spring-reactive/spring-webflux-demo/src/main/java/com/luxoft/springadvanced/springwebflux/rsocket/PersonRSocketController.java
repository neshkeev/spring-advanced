package com.luxoft.springadvanced.springwebflux.rsocket;

import com.luxoft.springadvanced.springwebflux.domain.Person;
import com.luxoft.springadvanced.springwebflux.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class PersonRSocketController {
    private final PersonRepository personRepository;

    @Value("${spring.rsocket.server.port}")
    Long port;

    @Autowired
    public PersonRSocketController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @MessageMapping("findById2") // request response
    Mono<Person> getPersonByIdForLoadBalancing(Long id) {
        if (port == 17000) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return personRepository.findById(1L)
                .doOnNext(person ->
                        System.out.println("Retrieved "+id+" on port "+port))
                .map(person -> new Person(
                        port,
                        id,
                        person.getName(),
                        person.getSurname()));
    }

    @MessageMapping("findById") // request response
    Mono<Person> getPersonById(Long id) {
        return personRepository.findById(1L);
    }

    @MessageMapping("all") // request stream
    Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @MessageMapping("add") // fire and forget
    Mono<Void> addPerson(Person person) {
        personRepository.save(person);
        return Mono.empty();
    }

    @MessageMapping("byId") // channel
    Flux<Person> getPersonByIds(Flux<Long> ids) {
        return ids.concatMap(personRepository::findById);
    }
}
