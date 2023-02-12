package com.luxoft.springadvanced.springwebclient;

import com.luxoft.springadvanced.springwebclient.SimpleRestController.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleWebClientBuilderTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebClient.Builder webClientBuilder;
    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = webClientBuilder
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void test() {
        final var value = new Value("JSESSIONID", "-1");

        final var cookieResponse = webClient.get()
                .uri("/cookie")
                .exchangeToMono(data -> data.bodyToMono(Value.class))
                .block();

        assertThat(cookieResponse, is(equalTo(value)));
    }

    @TestConfiguration
    static class MyConfig {

        @Bean
        public WebClient.Builder webClient() {
            return WebClient.builder()
                    .defaultCookie("JSESSIONID", "-1")
                    .defaultHeader("user-agent", "web-client-builder-test");
        }
    }
}
