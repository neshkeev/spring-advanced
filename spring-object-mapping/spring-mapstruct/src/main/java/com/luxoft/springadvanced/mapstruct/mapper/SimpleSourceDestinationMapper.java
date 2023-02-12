package com.luxoft.springadvanced.mapstruct.mapper;

import com.luxoft.springadvanced.mapstruct.domain.Destination;
import com.luxoft.springadvanced.mapstruct.dto.Source;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@SuppressWarnings("unused")
@Mapper(componentModel = ComponentModel.SPRING)
public interface SimpleSourceDestinationMapper {

    Destination sourceToDestination(Source source);

    Source destinationToSource(Destination destination);
}
