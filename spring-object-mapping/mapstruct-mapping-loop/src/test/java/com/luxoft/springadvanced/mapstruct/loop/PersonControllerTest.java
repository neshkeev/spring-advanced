package com.luxoft.springadvanced.mapstruct.loop;

import com.luxoft.springadvanced.mapstruct.loop.domain.PersonController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PersonControllerTest {
    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach(@Autowired PersonController personController) {
        webTestClient = WebTestClient
                .bindToController(personController)
                .build();
    }

    @Test
    public void test() {
        webTestClient.get()
                .uri("/persons")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
