package com.luxoft.springadvanced.springdatarest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringJUnitConfig
public class SpringDataRestRepositoryDetectionTest {

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

        assertThat(clientResponse.block(), is(HttpStatus.NOT_FOUND));
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

    @TestConfiguration
    static class MyConfig {

        @Bean
        public RepositoryRestConfigurer repositoryRestConfigurer() {
            return new RepositoryRestConfigurer() {
                @Override
                public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
                    config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
                }
            };
        }
    }
}