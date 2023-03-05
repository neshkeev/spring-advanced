package com.luxoft.springadvanced.springtestingmvc.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PassengerController {

    private final PassengerRepository passengerRepository;

    public PassengerController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @GetMapping("/passengers")
    public List<Passenger> getPassengers() {
        return passengerRepository.findAll();
    }

    @PostMapping("/passengers")
    @ResponseStatus(HttpStatus.CREATED)
    public Passenger save(@RequestBody Passenger passenger) {
        return passengerRepository.save(passenger);
    }
}
