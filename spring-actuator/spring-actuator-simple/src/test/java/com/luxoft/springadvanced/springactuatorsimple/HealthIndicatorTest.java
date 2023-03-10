package com.luxoft.springadvanced.springactuatorsimple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("health-indicators")
public class HealthIndicatorTest {
    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach(@LocalServerPort int port) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port + "/actuator/health")
                .build();
    }

    @Test
    public void testGetUp() {
        webTestClient.get()
                .uri("/helloUp")
                .exchange()
                .expectStatus().isOk()
                .expectBody(HealthStatus.class)
                .isEqualTo(new HealthStatus("UP"));
    }

    @Test
    public void testGetDown() {
        webTestClient.get()
                .uri("/helloDown")
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(503))
                .expectBody(HealthStatus.class)
                .isEqualTo(new HealthStatus("DOWN"));
    }

    private record HealthStatus(String status){}
}
