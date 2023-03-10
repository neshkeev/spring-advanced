package com.luxoft.springadvanced.springactuatorsimple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("base-path")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangeControlUrlTest {

    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach(@LocalManagementPort int port) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void testGet(@Value("${management.endpoints.web.base-path}") String endpoint) {
        webTestClient.head()
                .uri(endpoint)
                .exchange()
                .expectStatus().isOk();
    }
}
