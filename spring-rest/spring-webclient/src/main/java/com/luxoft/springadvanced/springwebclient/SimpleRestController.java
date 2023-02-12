package com.luxoft.springadvanced.springwebclient;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@RestController
public class SimpleRestController {

    @GetMapping("/locale")
    public Mono<Value> getLocale(Locale locale) {
        return Mono.just(new Value("locale", locale));
    }

    @GetMapping("/headers")
    public Flux<Value> headers(@RequestHeader Map<String, String> headers) {
        return Flux.fromStream(
                headers.entrySet().stream()
                        .map(e -> new Value(e.getKey(), e.getValue())));
    }

    @GetMapping("/cookie")
    public Mono<Value> cookie(@CookieValue("JSESSIONID") String sessionId) {
        return Mono.just(new Value("JSESSIONID", sessionId));
    }

    @GetMapping("/timezone")
    public Flux<Value> timeZone(TimeZone timeZone, ZoneId zoneId) {
        return Flux.concat(
                Mono.just(new Value("timeZone", timeZone.getDisplayName())),
                Mono.just(new Value("zoneId", zoneId)));
    }

    @GetMapping("/request")
    public Flux<Value> requestAndSession(WebRequest request,
                                         NativeWebRequest nativeWebRequest,
                                         HttpSession session) {
        return Flux.fromStream(
                request.getParameterMap()
                        .entrySet()
                        .stream()
                        .map(e -> new Value(e.getKey(), e.getValue()[0])));
    }

    @PostMapping("/records")
    public Mono<Value> addRecord(@RequestBody Value value) {
        return Mono.just(value);
    }

    public record Value(String name, Object value) {}

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<Value> exceptionHandler(MissingRequestCookieException ex) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Value("error", ex.getMessage()));
    }
}
