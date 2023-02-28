package com.luxoft.springadvanced.mapstruct;

import com.luxoft.springadvanced.mapstruct.domain.Destination;
import com.luxoft.springadvanced.mapstruct.dto.Source;
import com.luxoft.springadvanced.mapstruct.mapper.Customer;
import com.luxoft.springadvanced.mapstruct.mapper.CustomerRepository;
import com.luxoft.springadvanced.mapstruct.mapper.MapperService;
import com.luxoft.springadvanced.mapstruct.mapper.SimpleSourceDestinationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SourceDestinationMapperIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void before() {
        final var customer = new Customer();
        customer.setName("Hello");
        customerRepository.save(customer);
    }

    @Test
    @DisplayName("Given a Source with String fields, when we map it to a Destination with the same fields names, then the fields are correctly mapped")
    public void testMapSourceToDestination(@Autowired MapperService converter) {
        final var simpleSource = new Source("SourceName", "SourceDescription");

        final var destinations = converter.sourceToDestination(List.of(simpleSource));

        final var destination = destinations.get(0);

        assertNotEquals(simpleSource.name(), destination.name());
        assertEquals(simpleSource.description(), destination.description());
    }

    @Test
    @DisplayName("Given a Destination with String fields, when we map it to a Source with the same fields names, then the fields are correctly mapped")
    public void testMapDestinationToSource(@Autowired SimpleSourceDestinationMapper simpleSourceDestinationMapper) {
        final var destination = new Destination("DestinationName", "DestinationDescription");
        final var source = simpleSourceDestinationMapper.destinationToSource(destination);

        assertEquals(destination.name(), source.name());
        assertEquals(destination.description(), source.description());
    }
}
