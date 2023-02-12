package com.luxoft.springadvanced.projections;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.config.HypermediaWebTestClientConfigurer;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectionTest {

    private WebTestClient webClient;

    @Autowired
    private HypermediaWebTestClientConfigurer hypermediaWebTestClientConfigurer;

    @BeforeEach
    public void before(@LocalServerPort int port)  {
        webClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testNoProjection() {
        webClient.mutateWith(hypermediaWebTestClientConfigurer)
                .get()
                .uri("/customers/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new TypeReferences.EntityModelType<EntityModel<Map<String, String>>>())
                .consumeWith(e -> {
                    var content = e.getResponseBody().getContent().getContent();
                    assertThat(content.keySet(), Matchers.containsInAnyOrder("firstname", "lastname", "address", "gender"));
                });
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testFirstNameLastNameProjection() {
        webClient.mutateWith(hypermediaWebTestClientConfigurer)
                .get()
                .uri("/customers/1?projection=firstName,lastName")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new TypeReferences.EntityModelType<EntityModel<Map<String, String>>>())
                .consumeWith(e -> {
                    var content = e.getResponseBody().getContent().getContent();
                    assertThat(content.keySet(), Matchers.containsInAnyOrder("firstname", "lastname"));
                });
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testFirstNameLastNameAddressProjection() {
        webClient.mutateWith(hypermediaWebTestClientConfigurer)
                .get()
                .uri("/customers/1?projection=firstName,lastName,address")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new TypeReferences.EntityModelType<EntityModel<Map<String, String>>>())
                .consumeWith(e -> {
                    var content = e.getResponseBody().getContent().getContent();
                    assertThat(content.keySet(), Matchers.containsInAnyOrder("firstname", "lastname", "address"));
                });
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testFullList() {
        webClient.mutateWith(hypermediaWebTestClientConfigurer)
                .get()
                .uri("/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .consumeWith(e -> {
                    @SuppressWarnings("unchecked")
                    final Map<String, Map<String, List<Map<String, Object>>>> content = e.getResponseBody();
                    final var fieldNames = content.get("_embedded")
                            .get("customers")
                            .get(0)
                            .keySet()
                            .stream()
                            .filter(a -> !"_links".equals(a))
                            .toList();
                    assertThat(fieldNames, Matchers.containsInAnyOrder("firstname", "lastname", "address"));
                });
    }
}
