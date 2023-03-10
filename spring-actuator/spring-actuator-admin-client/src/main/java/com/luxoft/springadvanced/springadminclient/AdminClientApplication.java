package com.luxoft.springadvanced.springadminclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class AdminClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminClientApplication.class, args);
    }

//    @Configuration
//    @EnableWebSecurity
//    public static class SecurityPermitAllConfig {
//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            return http.authorizeHttpRequests().anyRequest().permitAll()
//                    .and().csrf().disable().build();
//        }
//    }
}
