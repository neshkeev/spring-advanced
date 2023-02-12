package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.App;
import com.luxoft.springadvanced.springdatarest.model.Review;
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

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringDataConditionalUpdateTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    public void testUpdateFail() {
        final var responseCode = getHttpResponseCode(Integer.MIN_VALUE);
        assertThat(responseCode, is(HttpStatusCode.valueOf(HttpStatus.PRECONDITION_FAILED.value())));
    }

    @Test
    public void testUpdateSuccess() {
        final var responseCode = getHttpResponseCode(0);
        assertThat(responseCode, is(HttpStatusCode.valueOf(HttpStatus.OK.value())));
    }

    private HttpStatusCode getHttpResponseCode(int version) {
        final Review review = createReview(version);
        return webClient.put()
                .uri("/reviews/d5e8c243-9246-4d01-9e7f-f693b8e932a1")
                .header(HttpHeaders.IF_MATCH, String.format("\"%d\"", version))
                .body(Mono.just(review), Review.class)
                .exchangeToMono(x -> Mono.just(x.statusCode()))
                .block();
    }

    private static Review createReview(int version) {
        final String appName = "Dropbox";

        final App app = new App();
        app.setId(UUID.nameUUIDFromBytes(appName.getBytes()));
        app.setName(appName);

        return new Review(UUID.fromString("d5e8c243-9246-4d01-9e7f-f693b8e932a1"),
                app,
                "Hello, World",
                (byte) 4,
                version
        );
    }
}