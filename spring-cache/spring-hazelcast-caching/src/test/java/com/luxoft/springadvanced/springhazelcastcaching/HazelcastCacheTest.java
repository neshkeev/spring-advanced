package com.luxoft.springadvanced.springhazelcastcaching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HazelcastCacheTest {
    private WebTestClient client;

    @LocalServerPort int port;

    @BeforeEach
    public void beforeEach() {
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        client.get()
                .uri("/books/123")
                .exchange();
    }

    @Test
    public void test() {
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofMillis(100))
                .build();

        client.get()
                .uri("/books/123")
                .exchange()
                .expectStatus().isOk();
    }
}
