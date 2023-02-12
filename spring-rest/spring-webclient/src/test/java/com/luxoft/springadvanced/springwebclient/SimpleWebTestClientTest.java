package com.luxoft.springadvanced.springwebclient;

import com.luxoft.springadvanced.springwebclient.SimpleRestController.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleWebTestClientTest {
    @LocalServerPort
    private int port;

    private WebTestClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void testCookie() {
        final var value = new Value("JSESSIONID", "-1");

        webClient.get()
                .uri("/cookie")
                .cookie(value.name(), value.value().toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Value.class).isEqualTo(value);
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
}
