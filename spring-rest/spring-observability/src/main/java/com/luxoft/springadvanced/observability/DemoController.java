package com.luxoft.springadvanced.observability;

import com.luxoft.springadvanced.springwebclient.SimpleRestController.Value;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    @ApiResponse(responseCode = "200", description = "Simply returns 'Hello, World!'")
    @GetMapping("/hello")
    public Mono<Value> hello() {
        return Mono.just(new Value("greeting", "Hello, world!"));
    }
}
