package com.luxoft.springadvanced.auth.serviceaccounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "keycloak", url = "http://localhost:18080/realms/myrealm/protocol/openid-connect")
public interface KeycloakClient {

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response getAccessToken(@RequestBody Map<String, ?> request);

    record Response(
            @JsonProperty("access_token")
            String accessToken,
            @JsonProperty("expires_in")
            int expiresIn,
            @JsonProperty("not_before_policy")
            int notBeforePolicy,
            @JsonProperty("refresh_expires_in")
            int refreshExpiresIn,
            String scope,
            @JsonProperty("token_type")
            String tokenType
    ) {}
}
