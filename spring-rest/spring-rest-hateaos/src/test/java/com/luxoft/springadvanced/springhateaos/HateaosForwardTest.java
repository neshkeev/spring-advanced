package com.luxoft.springadvanced.springhateaos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.config.HypermediaWebTestClientConfigurer;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HateaosForwardTest {
    @LocalServerPort
    private int port;

    @Autowired
    WebTestClient client;
    @Autowired
    HypermediaWebTestClientConfigurer hypermediaWebTestClientConfigurer;

    @Test
    public void test() throws MalformedURLException {
        final String forward = "https://example.com:9001/hateaos/apps";
        var allAppsUrl = new URL(forward);
        final String tiktok = allAppsUrl + "/TikTok";
        var tiktokUrl = new URL(tiktok);

        final String allApps = "http://localhost:" + port + "/hateaos/apps";
        final String tiktokUrlOriginal = allApps + "/TikTok";

        client.mutateWith(hypermediaWebTestClientConfigurer)
                .get()
                .uri(tiktokUrlOriginal)
                .header("X-Forwarded-Proto", allAppsUrl.getProtocol())
                .header("X-Forwarded-Host", allAppsUrl.getHost())
                .header("X-Forwarded-Port", Integer.toString(allAppsUrl.getPort()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(new TypeReferences.EntityModelType<EntityModel<HashMap<String, String>>>())
                .consumeWith(e -> {
                    var model = Objects.requireNonNull(e.getResponseBody());
                    assertAll(
                            () -> assertThat(model.getRequiredLink(IanaLinkRelations.SELF), is(equalTo(Link.of(tiktokUrl.toString())))),
                            () -> assertThat(model.getRequiredLink("apps"), is(equalTo(Link.of(allAppsUrl.toString(), LinkRelation.of("apps")))))
                    );
                })
        ;
    }

    @TestConfiguration
    static class MyConfig {
        @Bean
        ForwardedHeaderFilter forwardedHeaderFilter() {
            return new ForwardedHeaderFilter();
        }
    }
}