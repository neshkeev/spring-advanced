package com.luxoft.springadvanced.auth.serviceaccounts;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Profile({ "test" })
@ActiveProfiles({ "test" })
@EnableFeignClients
public class ServiceAccountTest {
    @Autowired
    KeycloakClient client;

    @Disabled("The test requires external keycloak, so it's disabled by default")
    @Test
    public void test() {
        final var clientId = System.getProperty("clientId");
        final var clientSecret = System.getProperty("clientSecret");
        final var request = Map.of("client_id", clientId,
                "client_secret", clientSecret,
                "grant_type", "client_credentials");
        final var accessToken = client.getAccessToken(request);

        assertThat(accessToken.accessToken(), is(notNullValue()));

        System.out.println(accessToken.accessToken());
    }
}
