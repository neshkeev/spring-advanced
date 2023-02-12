package com.luxoft.springadvanced.mapstruct;

import com.luxoft.springadvanced.mapstruct.domain.Destination;
import com.luxoft.springadvanced.mapstruct.dto.Source;
import com.luxoft.springadvanced.mapstruct.mapper.SimpleSourceDestinationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SpringBootConfiguration
@ComponentScan
public class SourceDestinationMapperIntegrationTest {

    @Test
    @DisplayName("Given a Source with String fields, when we map it to a Destination with the same fields names, then the fields are correctly mapped")
    public void testMapSourceToDestination(@Autowired SimpleSourceDestinationMapper simpleSourceDestinationMapper) {
        final var simpleSource = new Source("SourceName", "SourceDescription");

        final var destination = simpleSourceDestinationMapper.sourceToDestination(simpleSource);

        assertEquals(simpleSource.name(), destination.name());
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
