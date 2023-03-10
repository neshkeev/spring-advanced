package com.luxoft.springadvanced.springactuatorsimple;

import com.luxoft.springadvanced.springactuatorsimple.endpoint.HelloEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloEndpointTest {

    private WebTestClient webTestClient;

    @BeforeEach
    public void beforeEach(@LocalServerPort int port) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port + "/actuator")
                .build();
    }

    @Test
    public void testGet() {
        webTestClient.get()
                .uri("/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(HelloEndpoint.Data.class)
                .isEqualTo(new HelloEndpoint.Data("Hello", "World"));
    }

    @Test
    public void testPost() {
        webTestClient.post()
                .uri("/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new PostData("John", 1)), PostData.class)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    @Test
    public void testDelete() {
        webTestClient.delete()
                .uri("/hello")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    private record PostData(String name, int id) {}
}
