package com.luxoft.springadvanced.springtestingwebflux;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = NumbersController.class)
public class WebTestClientTest {

    @SpyBean
    private NumbersController controller;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void test() {
        webClient.get()
                .uri("/numbers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .contains(1, 2, 3, 4);

        verify(controller, times(1)).getNumbers();
    }
}