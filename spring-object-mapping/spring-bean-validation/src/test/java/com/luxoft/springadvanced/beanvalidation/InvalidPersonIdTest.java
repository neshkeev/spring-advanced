package com.luxoft.springadvanced.beanvalidation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvalidPersonIdTest {
    private WebTestClient webClient;

    @BeforeEach
    public void before(@LocalServerPort int port)  {
        webClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void test() {
        webClient.get()
                .uri("/persons/0")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Errors.class)
                .consumeWith(e -> {
                            final var responseBody = Objects.requireNonNull(e.getResponseBody());
                            assertThat(responseBody.errors(), contains("must be greater than or equal to 1"));
                        }
                );
    }

    record Errors(List<String> errors) {}
}
