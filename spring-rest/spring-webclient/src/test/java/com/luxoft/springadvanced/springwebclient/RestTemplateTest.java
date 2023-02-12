package com.luxoft.springadvanced.springwebclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {

    @LocalServerPort
    private int port;

    private String rootUrl;

    @BeforeEach
    public void beforeEach() {
        rootUrl = "http://localhost:" + port;
    }

    @Test
    public void testEntity() {
        final var restTemplate = new RestTemplate();
        ResponseEntity<SimpleRestController.Value> response = restTemplate.getForEntity(
                rootUrl + "/locale", SimpleRestController.Value.class);
        //noinspection ConstantConditions
        assertAll(
                () -> assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK))),
                () -> assertThat(response.getBody().value(), is(equalTo(Locale.getDefault().toString())))
        );
    }

    @Test
    public void testObject() {
        final var restTemplate = new RestTemplate();
        final var response = restTemplate.getForObject(
                rootUrl + "/locale", SimpleRestController.Value.class);

        //noinspection ConstantConditions
        assertThat(response.value(), is(equalTo(Locale.getDefault().toString())));
    }
}