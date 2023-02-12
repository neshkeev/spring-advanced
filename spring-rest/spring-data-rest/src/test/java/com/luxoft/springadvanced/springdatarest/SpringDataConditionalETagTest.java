package com.luxoft.springadvanced.springdatarest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringDataConditionalETagTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    public void testETagGet() {
        final var headers = getResponseHeaders();
        //noinspection ConstantConditions
        assertAll(
                () -> assertThat(headers, Matchers.hasKey(HttpHeaders.ETAG)),
                () -> assertThat(headers.get(HttpHeaders.ETAG).get(0), is("\"0\""))
        );
    }


    private HttpHeaders getResponseHeaders() {
        return webClient.get()
                .uri("/reviews/d5e8c243-9246-4d01-9e7f-f693b8e932a1")
                .exchangeToMono(x -> Mono.just(x.headers()))
                .map(ClientResponse.Headers::asHttpHeaders)
                .block();
    }

}