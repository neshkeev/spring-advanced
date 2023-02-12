package com.luxoft.springadvanced.beanvalidation;

import com.luxoft.springadvanced.beanvalidation.domain.Person;
import com.luxoft.springadvanced.beanvalidation.domain.PersonController;
import com.luxoft.springadvanced.beanvalidation.domain.PersonRepository;
import com.luxoft.springadvanced.beanvalidation.error.validator.CountryValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class SimpleValidationTest {
    private WebTestClient webClient;

    @SuppressWarnings("JUnitMalformedDeclaration")
    @BeforeEach
    public void before(@Autowired PersonController controller, @Autowired PersonRepository repository)  {
        repository.deleteAll();
        webClient = WebTestClient
                .bindToController(controller)
                .build();
    }

    @Test
    public void testGetAll() {
        webClient.get()
                .uri("/persons")
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class).isEqualTo(List.of());
    }

    @Test
    public void testPost() {
        final Person person = new Person();
        person.setCountry(CountryValidator.FourAsianTigers.SINGAPORE.getName());
        person.setName("John Wu");
        person.setSalary(BigDecimal.valueOf(1500.00));

        webClient.post()
                .uri("/persons")
                .body(Mono.just(person), Person.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Person.class)
                .consumeWith(e -> {
                            final Person savedPerson = Objects.requireNonNull(e.getResponseBody());
                            assertAll(
                                    () -> assertThat(savedPerson.getId(), is(notNullValue())),
                                    () -> assertThat(savedPerson.getCountry(), is(equalTo(person.getCountry()))),
                                    () -> assertThat(savedPerson.getName(), is(equalTo(person.getName()))),
                                    () -> assertThat(savedPerson.getSalary(), is(equalTo(person.getSalary())))
                            );
                        }
                );
    }
}
