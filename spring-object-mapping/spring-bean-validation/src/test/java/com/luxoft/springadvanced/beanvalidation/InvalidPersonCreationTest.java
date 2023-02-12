package com.luxoft.springadvanced.beanvalidation;

import com.luxoft.springadvanced.beanvalidation.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvalidPersonCreationTest {
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
        final Person person = new Person();

        final String[] expectedErrors = {
                "Please provide a name",
                "Please provide a salary",
                "Please provide a country",
                "Country is not allowed and cannot be empty"
        };
        webClient.post()
                .uri("/persons")
                .body(Mono.just(person), Person.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Errors.class)
                .consumeWith(e -> {
                            final var responseBody = Objects.requireNonNull(e.getResponseBody());
                            assertThat(responseBody.errors(), containsInAnyOrder(expectedErrors));
                        }
                );
    }

    record Errors(List<String> errors) {}
}
