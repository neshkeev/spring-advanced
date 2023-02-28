package com.luxoft.springadvanced.mapstruct.mapper;

import com.luxoft.springadvanced.mapstruct.domain.Destination;
import com.luxoft.springadvanced.mapstruct.dto.Source;

import java.util.List;

public interface MapperService {
    @SuppressWarnings("unused")
    Destination sourceToDestination(Source source);
    List<Destination> sourceToDestination(List<Source> source);
}
