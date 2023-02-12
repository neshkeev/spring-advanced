package com.luxoft.springadvanced.springdatarest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringDataConditionalSelectTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    public void testEmptyGet() {
        final var responseCode = getHttpResponseCode(0);
        assertThat(responseCode, is(HttpStatusCode.valueOf(HttpStatus.NOT_MODIFIED.value())));
    }

    @Test
    public void testFullGet() {
        final var responseCode = getHttpResponseCode(Integer.MIN_VALUE);
        assertThat(responseCode, is(HttpStatusCode.valueOf(HttpStatus.OK.value())));
    }

    private HttpStatusCode getHttpResponseCode(int version) {
        return webClient.get()
                .uri("/reviews/d5e8c243-9246-4d01-9e7f-f693b8e932a1")
                .header(HttpHeaders.IF_NONE_MATCH, String.format("\"%d\"", version))
                .exchangeToMono(x -> Mono.just(x.statusCode()))
                .block();
    }

}