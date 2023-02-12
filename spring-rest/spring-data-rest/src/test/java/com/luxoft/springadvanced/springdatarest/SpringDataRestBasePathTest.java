package com.luxoft.springadvanced.springdatarest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringDataRestBasePathTest {
    public static final String BASE_PATH = "/api/rest";

    private WebTestClient webClient;

    @BeforeEach
    public void before(@LocalServerPort int port) {
        webClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void testApps() {
        getHttpResponseCode(BASE_PATH + "/apps")
                .expectStatus().isOk();
    }

    @Test
    public void testReviews() {
        getHttpResponseCode(BASE_PATH + "/reviews")
                .expectStatus().isOk();
    }

    @Test
    public void testAppsNotFound() {
        getHttpResponseCode("/apps")
                .expectStatus().isNotFound();
    }

    @Test
    public void testReviewsNotFound() {
        getHttpResponseCode("/reviews")
                .expectStatus().isNotFound();
    }

    private WebTestClient.ResponseSpec getHttpResponseCode(String uri) {
        return webClient.get()
                .uri(uri)
                .exchange();
    }

    @TestConfiguration
    static class MyConfig {
        @Bean
        public RepositoryRestConfigurer repositoryRestConfigurer() {
            return new RepositoryRestConfigurer() {

                @Override
                public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
                    config.setBasePath(BASE_PATH);
                }
            };
        }
    }
}