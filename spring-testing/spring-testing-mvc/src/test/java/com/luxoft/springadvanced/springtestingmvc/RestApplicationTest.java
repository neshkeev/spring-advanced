package com.luxoft.springadvanced.springtestingmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.springadvanced.springtestingmvc.model.Country;
import com.luxoft.springadvanced.springtestingmvc.model.CountryRepository;
import com.luxoft.springadvanced.springtestingmvc.model.Passenger;
import com.luxoft.springadvanced.springtestingmvc.model.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestApplicationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PassengerRepository passengerRepository;

    @MockBean
    private CountryRepository countryRepository;

    @BeforeEach
    public void beforeEach() {
        final var countries = List.of(new Country("Hong Kong", "HK"),
                new Country("South Korea", "SK"),
                new Country("Taiwan", "TW"),
                new Country("Singapore", "SG")

        );
        when(countryRepository.findAll()).thenReturn(countries);

        when(passengerRepository.findAll()).thenReturn(
                List.of(new Passenger("John Smith", countries.get(new Random().nextInt(4))),
                        new Passenger("Jane Doe", countries.get(new Random().nextInt(4))),
                        new Passenger("Patty Jones", countries.get(new Random().nextInt(4))),
                        new Passenger("Solomon Ricks", countries.get(new Random().nextInt(4)))
        ));
    }

    @Test
    void testGetAllCountries() throws Exception {
        mvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));

        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPassengers() throws Exception {
        mvc.perform(get("/passengers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));

        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    void testPostPassenger() throws Exception {
        final var country = new Country("China", "CH");
        final var passenger = new Passenger("Peter Thomson", country);

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        mvc.perform(post("/passengers")
                .content(new ObjectMapper().writeValueAsString(passenger))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(passenger.getName())))
                .andExpect(jsonPath("$.country.codeName", is(country.getCodeName())))
                .andExpect(jsonPath("$.country.name", is(country.getName())))
                .andExpect(jsonPath("$.registered", is(Boolean.FALSE)));

        verify(passengerRepository, times(1)).save(passenger);
    }
}
