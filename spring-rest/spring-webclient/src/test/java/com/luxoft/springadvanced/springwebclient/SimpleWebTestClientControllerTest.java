package com.luxoft.springadvanced.springwebclient;

import com.luxoft.springadvanced.springwebclient.SimpleRestController.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@SpringBootTest
public class SimpleWebTestClientControllerTest {
    private WebTestClient webClient;

    @BeforeEach
    public void before(@Autowired SimpleRestController controller) {
        webClient = WebTestClient
                .bindToController(controller)
                .build();
    }

    @Test
    public void testCookie() {
        webClient.get()
                .uri("/cookie")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .isEmpty();
    }

    @Test
    public void testHeader() {
        final var value = new Value("user-agent", "web-test-client");

        webClient.get()
                .uri("/headers")
                .header(value.name(), value.value().toString())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Value.class)
                .consumeWith(r -> assertThat(r.getResponseBody(), hasItem(equalTo(value))));
    }

    @Test
    public void testPost() {
        final var value = new Value("user-agent", "web-test-client");

        webClient.post()
                .uri("/records")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(value), Value.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Value.class).isEqualTo(value);
    }
}
