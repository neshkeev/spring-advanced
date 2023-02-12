package com.luxoft.springadvanced.mapstruct.loop;

import com.luxoft.springadvanced.mapstruct.loop.domain.Country;
import com.luxoft.springadvanced.mapstruct.loop.domain.Person;
import com.luxoft.springadvanced.mapstruct.loop.domain.PersonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final PersonRepository personRepository;

    public Application(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void postConstruct() {
        final var ivan = new Person("Ivan");
        ivan.setCountry(new Country("RU", "Russia"));
        personRepository.save(ivan);
    }
}
