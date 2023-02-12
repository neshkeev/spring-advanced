package com.luxoft.springadvanced.mapstruct.loop.domain;

import com.luxoft.springadvanced.mapstruct.loop.dto.PersonDTO;
import com.luxoft.springadvanced.mapstruct.loop.mapper.PersonMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class PersonDTOController {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonDTOController(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @GetMapping("/dto/persons")
    public Flux<PersonDTO> getPersons() {
        return Flux.fromIterable(personRepository.findAll())
                .map(personMapper::personToPersonDTO);
    }
}
