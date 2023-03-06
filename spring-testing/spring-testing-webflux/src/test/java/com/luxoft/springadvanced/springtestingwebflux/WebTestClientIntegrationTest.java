package com.luxoft.springadvanced.springtestingwebflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebTestClientIntegrationTest {

    private WebTestClient webClient;

    @BeforeEach
    public void beforeEach(@LocalServerPort int port) {
        webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void test() {
        webClient.get()
                .uri("/numbers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .isEqualTo(List.of(0, 1, 2, 3, 4));
    }
}
