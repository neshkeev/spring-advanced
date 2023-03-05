package com.luxoft.springadvanced.springtestingwebflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class NumbersController {

    @GetMapping("/numbers")
    public Flux<Integer> getNumbers() {
        return Flux.range(0, 5);
    }
}
