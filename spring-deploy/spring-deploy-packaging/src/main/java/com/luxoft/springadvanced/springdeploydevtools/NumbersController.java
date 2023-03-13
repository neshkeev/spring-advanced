package com.luxoft.springadvanced.springdeploydevtools;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api")
public class NumbersController {

    @GetMapping("/numbers")
    public Flux<Integer> getNumbers() {
        return Flux.range(0, 5);
    }

    @GetMapping("/hostname")
    public Mono<String> hostname() throws UnknownHostException {
        final String hostName = InetAddress.getLocalHost().getHostName();
        return Mono.just(hostName);
    }
}
