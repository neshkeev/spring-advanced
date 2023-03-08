package com.luxoft.springadvanced.springproxypostprocessor;

import com.luxoft.springadvanced.springproxypostprocessor.model.User;
import com.luxoft.springadvanced.springproxypostprocessor.model.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;

@SpringBootApplication
public class BeanPostProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeanPostProcessorApplication.class, args);
    }

    public BeanPostProcessorApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;
    @PostConstruct
    public void postConstruct() {
        Stream.of("Jane", "John", "Nick", "Sid")
                .map(User::new)
                .forEach(userRepository::save);

    }
}