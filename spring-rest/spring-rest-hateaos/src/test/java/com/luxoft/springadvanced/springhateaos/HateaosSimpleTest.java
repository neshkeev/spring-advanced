package com.luxoft.springadvanced.springhateaos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.config.HypermediaWebTestClientConfigurer;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashMap;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HateaosSimpleTest {
    @LocalServerPort
    private int port;

    @Autowired
    WebTestClient client;
    @Autowired
    HypermediaWebTestClientConfigurer hypermediaWebTestClientConfigurer;

    @Test
    public void test() {
        final String allApps = "http://localhost:" + port + "/hateaos/apps";
        final String tiktokUrl = allApps + "/TikTok";
        client.mutateWith(hypermediaWebTestClientConfigurer)
                .get()
                .uri(tiktokUrl)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new TypeReferences.EntityModelType<EntityModel<HashMap<String, String>>>())
                .consumeWith(e -> {
                    var model = Objects.requireNonNull(e.getResponseBody());
                    //noinspection ConstantConditions
                    assertAll(
                            () -> assertThat(model.getRequiredLink(IanaLinkRelations.SELF), is(equalTo(Link.of(tiktokUrl)))),
                            () -> assertThat(model.getRequiredLink("apps"), is(equalTo(Link.of(allApps, LinkRelation.of("apps"))))),
                            () -> assertThat(model.getContent().getContent().get("name"), is(equalTo("TikTok")))
                    );
                })
        ;
    }
}