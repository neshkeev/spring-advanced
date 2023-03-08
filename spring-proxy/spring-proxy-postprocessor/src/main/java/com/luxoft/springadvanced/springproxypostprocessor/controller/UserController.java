package com.luxoft.springadvanced.springproxypostprocessor.controller;

import com.luxoft.springadvanced.springproxypostprocessor.model.User;
import com.luxoft.springadvanced.springproxypostprocessor.model.UserRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Flux<User> getUsers() {
        return Flux.fromIterable(userRepository.findAll());

    }
    @GetMapping("/users/{id}")
    public Mono<User> getUser(@PathVariable int id) {
        return userRepository.findById(id)
                .map(Mono::just)
                .orElse(Mono.empty());
    }

    @PostMapping("/users")
    public Mono<User> addUser(@RequestBody User user) {
        return Mono.just(userRepository.save(user));
    }
}
