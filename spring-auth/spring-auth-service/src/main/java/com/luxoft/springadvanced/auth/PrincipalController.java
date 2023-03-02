package com.luxoft.springadvanced.auth;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Optional;

@RestController
public class PrincipalController {

    @GetMapping(path = "/")
    public String index(Authentication auth, Model model) {
        return prepareUsername(auth, model);
    }

    @GetMapping(path = "/profile")
    public String profile(Authentication auth, Model model, Locale locale) {
        return prepareUsername(auth, model);
    }

    private static String prepareUsername(Authentication auth, Model model) {
        final var username = Optional.ofNullable(auth)
                .map(Authentication::getPrincipal)
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(AuthenticatedPrincipal::getName)
                .orElse("Anonymous");

        model.addAttribute("username", username);
        return "<h1>Hello, " + username + "!</h1>";
    }
}
