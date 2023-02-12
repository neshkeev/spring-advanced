package com.luxoft.springadvanced.springwebclient;

import com.luxoft.springadvanced.springwebclient.SimpleRestController.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleWebClientTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    public void test() {
        final var value = new Value("JSESSIONID", "-1");

        final var cookieResponse = webClient.get()
                .uri("/cookie")
                .cookie(value.name(), value.value().toString())
                .exchangeToMono(data -> data.bodyToMono(Value.class))
                .block();

        assertThat(cookieResponse, is(equalTo(value)));
    }

    @Test
    public void testHeaders() {
        final var value = new Value("user-agent", "simple-web-client-test");

        final var cookieResponse = webClient.get()
                .uri("/headers")
                .header(value.name(), value.value().toString())
                .exchangeToFlux(data -> data.bodyToFlux(Value.class))
                .toIterable();

        assertThat(cookieResponse, hasItem(equalTo(value)));
    }

    @Test
    public void testEmptyCookiesExceptionHandler() {
        final var value = new Value("error", "Required cookie 'JSESSIONID' for method parameter type String is not present");

        final var cookieResponse = webClient.get()
                .uri("/cookie")
                .exchangeToMono(data -> data.bodyToMono(Value.class))
                .block();

        assertThat(cookieResponse, is(equalTo(value)));
    }
}
