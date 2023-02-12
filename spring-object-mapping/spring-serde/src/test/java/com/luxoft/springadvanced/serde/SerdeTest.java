package com.luxoft.springadvanced.serde;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class SerdeTest {

    private WebTestClient webClient;

    @BeforeEach
    public void before(@Autowired ItemController itemController)  {
        webClient = WebTestClient
                .bindToController(itemController)
                .build();
    }

    @Test
    public void testGetMap() {
        webClient.get()
                .uri("/serde/items")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .consumeWith(e -> {
                    @SuppressWarnings("unchecked")
                    Map<String, String> responseBody = (Map<String, String>) e.getResponseBody();
                    //noinspection ConstantConditions
                    assertThat(responseBody.keySet(), Matchers.containsInAnyOrder("id","name", "owner", "ownerName"));
                });
    }

    @Test
    public void testPostMap() {
        webClient.post()
                .uri("/serde/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just("{\"id\":1024,\"name\":\"TV\",\"owner\":13,\"ownerName\":\"John\"}"), String.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .consumeWith(e -> {
                    @SuppressWarnings("unchecked")
                    Map<String, String> responseBody = (Map<String, String>) e.getResponseBody();
                    //noinspection ConstantConditions
                    assertThat(responseBody.keySet(), Matchers.containsInAnyOrder("id","name", "owner", "ownerName"));
                });
    }
}
