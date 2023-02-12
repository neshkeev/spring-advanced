package com.luxoft.springadvanced.springdatarest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringJUnitConfig
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringDataRestPagingTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    public void testSorting() {
        final var clientResponse = getAppsReversed();
        final List<String> expected = Objects.requireNonNull(clientResponse)
                .stream()
                .sorted(Collections.reverseOrder())
                .toList();
        assertThat(clientResponse, is(equalTo(expected)));
    }

    private List<String> getAppsReversed() {
        return webClient.get()
                .uri("/apps?sort=name,desc")
                .exchangeToFlux(x -> x.bodyToFlux(Content.class))
                .map(Content::getEmbedded)
                .flatMap(e -> Flux.fromIterable(e.get("apps")))
                .map(e -> e.get("name"))
                .map(String.class::cast)
                .collectList()
                .block();
    }

    @Test
    public void testPaging() {
        final var clientResponse = getHttpResponseCode();

        assertAll(
                () -> assertThat(clientResponse.get("size"), is(5)),
                () -> assertThat(clientResponse.get("totalElements"), is(not(0))),
                () -> assertThat(clientResponse.get("totalPages"), is(Matchers.greaterThan(1)))
        );
    }

    private Map<String, Integer> getHttpResponseCode() {
        return webClient.get()
                .uri("/reviews?size=5")
                .exchangeToFlux(x -> x.bodyToFlux(Content.class))
                .map(Content::getPage)
                .blockFirst();
    }

    @SuppressWarnings("unused")
    public static class Content {
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