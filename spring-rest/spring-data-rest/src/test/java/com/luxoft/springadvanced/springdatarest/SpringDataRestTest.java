package com.luxoft.springadvanced.springdatarest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringDataRestTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    public void testApps() {
        final Mono<HttpStatusCode> clientResponse = getHttpResponseCode("/apps");

        assertThat(clientResponse.block(), is(HttpStatus.OK));
    }

    @Test
    public void testReviews() {
        final Mono<HttpStatusCode> clientResponse = getHttpResponseCode("/reviews");

        assertThat(clientResponse.block(), is(HttpStatus.OK));
    }

    private Mono<HttpStatusCode> getHttpResponseCode(String uri) {
        return webClient.get()
                .uri(uri)
                .exchangeToMono(Mono::just)
                .map(ClientResponse::statusCode);
    }
}