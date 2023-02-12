package com.luxoft.springadvanced.beanvalidation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbsentPersonIdTest {
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
                .uri("/persons/1")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Errors.class)
                .consumeWith(e -> {
                            final var responseBody = Objects.requireNonNull(e.getResponseBody());
                            assertThat(responseBody.errors(), is(equalTo("Person not found. id: 1")));
                        }
                );
    }

    record Errors(String errors) {}
}
