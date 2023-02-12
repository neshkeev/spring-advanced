package com.luxoft.springadvanced.beanvalidation.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          ServerWebExchange exchange) {
        //noinspection ConstantConditions
        final var errorMessages = ex.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        final Map<String, List<String>> errors = Map.of("errors", errorMessages);
        return Mono.just(new ResponseEntity<>(errors, headers, status));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<Object>> constraintViolationException(ConstraintViolationException ex) {
        final var errorMessages = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        final Map<String, List<String>> errors = Map.of("errors", errorMessages);
        return Mono.just(new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({PersonUnsupportedFieldPatchException.class, PersonNotFoundException.class})
    public Mono<ResponseEntity<Object>> userDefinedExceptions(Exception ex) {
        final Map<String, String> errors = Map.of("errors", ex.getMessage());

        return Mono.just(new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST));
    }
}
