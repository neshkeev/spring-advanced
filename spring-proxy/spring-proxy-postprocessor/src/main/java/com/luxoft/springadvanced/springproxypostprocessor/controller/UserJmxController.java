package com.luxoft.springadvanced.springproxypostprocessor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.springadvanced.springproxypostprocessor.jmx.JmxExporter;
import com.luxoft.springadvanced.springproxypostprocessor.model.UserRepository;
import org.springframework.stereotype.Component;

@Component
@JmxExporter
@SuppressWarnings("unused")
public class UserJmxController {
    private final UserRepository userRepository;

    public UserJmxController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void blockUser(Integer id) {
        changeUserStatus(id, false);
    }

    public void activateUser(Integer id) {
        changeUserStatus(id, true);
    }

    public String getUser(Integer id) throws JsonProcessingException {
        final var objectMapper = new ObjectMapper();

        final var user = userRepository.findById(id)
                .orElse(null);

        return objectMapper.writeValueAsString(user);
    }

    private void changeUserStatus(Integer id, boolean active) {
        userRepository.findById(id)
                .map(u -> {
                    u.setActive(active);
                    return u;
                })
                .ifPresent(userRepository::save);
    }
}
