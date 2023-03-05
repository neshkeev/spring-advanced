package com.luxoft.springadvanced.springtestingwebflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebClientTest {

    @MockBean
    private NumbersController controller;

    private WebClient webClient;

    @BeforeEach
    public void beforeEach(@LocalServerPort int port) {
        webClient = WebClient.create("http://localhost:" + port);

        Mockito.when(controller.getNumbers())
                .thenReturn(Flux.range(1,3));
    }

    @Test
    public void test() {
        final var response = webClient.get()
                .uri("/numbers")
                .exchangeToFlux(e -> e.bodyToFlux(Integer.class));

        StepVerifier.create(response)
                .expectNext(1)
                .expectNext(2, 3)
                .verifyComplete();

        verify(controller, times(1)).getNumbers();
    }
}
