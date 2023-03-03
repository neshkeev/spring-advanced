package com.luxoft.springadvanced.springwebflux.repo;

import com.luxoft.springadvanced.springwebflux.domain.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends R2dbcRepository<Person, Long> {

    Flux<Person> findByName(String name);
}
