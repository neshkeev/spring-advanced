package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.BaseConfig;
import jakarta.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringETagFilterTest extends BaseConfig {

    @LocalServerPort
    private int port;

    private WebClient webClient;
    private String etag;

    @BeforeEach
    public void before() {
        webClient = WebClient.create("http://localhost:" + port);
        etag = webClient.get()
                .uri("/myapps")
                .exchangeToMono(Mono::just)
                .map(ClientResponse::headers)
                .map(ClientResponse.Headers::asHttpHeaders)
                .map(HttpHeaders::getETag)
                .block();
    }

    @Test
    public void testEtagIfNoneMatch() {
        final var response = webClient.get()
                .uri("/myapps")
                .ifNoneMatch(etag)
                .exchangeToMono(Mono::just)
                .block();

        //noinspection ConstantConditions
        assertThat(response.statusCode(), is(HttpStatus.NOT_MODIFIED));
    }

    @Test
    public void testEtagIfMatch() {
        final var response = webClient.get()
                .uri("/myapps")
                .header("If-Match", etag)
                .exchangeToMono(Mono::just)
                .block();

        //noinspection ConstantConditions
        assertThat(response.statusCode(), is(HttpStatus.OK));
    }

    @TestConfiguration
    static class MyConfig {
        @Bean
        public FilterRegistrationBean<ShallowEtagHeaderFilter> someFilterRegistration() {
            FilterRegistrationBean<ShallowEtagHeaderFilter> registration
                    = new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());
            registration.addUrlPatterns("/myapps");
            registration.setName("ETagFilter");
            return registration;
        }

        @Bean(name = "ETagFilter")
        public Filter etagFilter() {
            return new ShallowEtagHeaderFilter();
        }
    }
}
