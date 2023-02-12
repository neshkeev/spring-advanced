package com.luxoft.springadvanced.mapstruct.loop;

import com.luxoft.springadvanced.mapstruct.loop.domain.PersonDTOController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PersonDTOControllerTest {
    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach(@Autowired PersonDTOController personDTOController) {
        webTestClient = WebTestClient
                .bindToController(personDTOController)
                .build();
    }

    @Test
    public void test() {
        webTestClient.get()
                .uri("/dto/persons")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .consumeWith(e -> {
                    final var responseBody = Objects.requireNonNull(e.getResponseBody());
                    assertThat(responseBody.size(), is(equalTo(1)));
                });
    }
}
