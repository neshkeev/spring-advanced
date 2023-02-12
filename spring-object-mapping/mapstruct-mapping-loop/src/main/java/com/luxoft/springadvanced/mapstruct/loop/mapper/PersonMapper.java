package com.luxoft.springadvanced.mapstruct.loop.mapper;

import com.luxoft.springadvanced.mapstruct.loop.domain.Person;
import com.luxoft.springadvanced.mapstruct.loop.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface PersonMapper {

    @Mapping(source = "person.country.name", target = "country")
    PersonDTO personToPersonDTO(Person person);
}
