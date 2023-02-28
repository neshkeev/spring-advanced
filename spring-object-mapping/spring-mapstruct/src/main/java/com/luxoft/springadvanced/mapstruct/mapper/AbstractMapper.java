package com.luxoft.springadvanced.mapstruct.mapper;

import com.luxoft.springadvanced.mapstruct.domain.Destination;
import com.luxoft.springadvanced.mapstruct.dto.Source;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AbstractMapper implements MapperService {
    @Autowired
    @SuppressWarnings("unused")
    private CustomerRepository customerRepository;

    public Destination sourceToDestination(Source source) {
        final var name = customerRepository.findById(1L)
                .map(Customer::getName)
                .orElse(source.name());
        return new Destination(name, source.description());
    }

    public abstract List<Destination> sourceToDestination(List<Source> source);
}
