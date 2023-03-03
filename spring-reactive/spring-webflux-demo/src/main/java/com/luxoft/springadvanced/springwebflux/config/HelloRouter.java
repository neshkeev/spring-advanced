package com.luxoft.springadvanced.springwebflux.config;

import com.luxoft.springadvanced.springwebflux.handlers.RootHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HelloRouter {
    @Bean
    public RouterFunction<ServerResponse> route(RootHandler rootHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/"),
                        rootHandler::root)
                .andRoute(RequestPredicates.GET("/hello"),
                        rootHandler::hello);
    }
}
