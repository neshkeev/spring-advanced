package com.luxoft.springadvanced.springrestevents;

import com.luxoft.springadvanced.springdatarest.model.App;
import com.luxoft.springadvanced.springdatarest.model.Review;
import com.luxoft.springadvanced.springrestevents.listener.ReviewEventHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringEventListenerTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream(4096);
    private PrintStream systemOUt;

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
        systemOUt = System.out;
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void afterEach() {
        System.setOut(systemOUt);
        output.reset();
    }

    @Test
    public void testSaveApp() {
        final var review = createApp("FB");

        webClient.post()
                .uri("/apps")
                .body(Mono.just(review), Review.class)
                .exchangeToMono(Mono::just)
                .block();

        systemOUt.println(output);

        assertThat(output.toString().contains("FB is prohibited from conducting its business on Russia's territory"), is(true));
    }

    @Test
    public void testSaveReview() {
        final Review review = createReview();

        webClient.post()
                .uri("/reviews")
                .body(Mono.just(review), Review.class)
                .exchangeToMono(Mono::just)
                .block();

        systemOUt.println(output);

        assertThat(output.toString().contains("ReviewEventHandler"), is(true));
    }

    private static Review createReview() {
        final App app = createApp("TikTok");
        return new Review(UUID.randomUUID(), app, "hello, world", (byte) 5, 0);
    }

    private static App createApp(String name) {
        return new App(UUID.nameUUIDFromBytes(name.getBytes()), name);
    }

    @TestConfiguration
    static class MyConfig {

        @Bean
        public ReviewEventHandler reviewEventHandler() {
            return new ReviewEventHandler();
        }
    }
}
