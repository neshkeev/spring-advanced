package com.luxoft.springadvanced.springdatarest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringDataProjectionsTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    public void testProjection() {
        final var clientResponse = getHttpResponseCode();

        assertAll(
                () -> assertThat(clientResponse, hasKey("app")),
                () -> assertThat(clientResponse, hasKey("text"))
        );
    }

    private Map<String, Object> getHttpResponseCode() {
        return webClient.get()
                .uri("/reviews?size=1")
                .exchangeToFlux(x -> x.bodyToFlux(Content.class))
                .map(Content::getEmbedded)
                .map(e -> e.get("reviews"))
                .map(e -> e.get(0))
                .blockFirst();
    }

    @SuppressWarnings("unused")
    private static class Content {
        @JsonProperty("_links")
        Object links;
        @JsonProperty("_embedded")
        Map<String, List<Map<String, Object>>> embedded;
        @JsonProperty("page")
        Map<String, Integer> page;

        public Object getLinks() {
            return links;
        }

        public void setLinks(Object links) {
            this.links = links;
        }

        public Map<String, List<Map<String, Object>>> getEmbedded() {
            return embedded;
        }

        public void setEmbedded(Map<String, List<Map<String, Object>>> embedded) {
            this.embedded = embedded;
        }

        public Map<String, Integer> getPage() {
            return page;
        }

        public void setPage(Map<String, Integer> page) {
            this.page = page;
        }
    }
}