package com.luxoft.springadvanced.springadminclient.model;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = { "countries" })
public class PassengerService {

    @Cacheable(value = "countries", key = "#passsenger.name")
    public Country getCacheableCountry(Passenger passenger) {
        return passenger.getCountry();
    }

    @Cacheable({ "countries", "directory" })
    public Country getCacheableCountryMultipleCaches(Passenger passenger) {
        return passenger.getCountry();
    }

}
