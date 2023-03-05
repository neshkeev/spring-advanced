package com.luxoft.springadvanced.springtesting.annotations.profile;

import com.luxoft.springadvanced.springtesting.annotations.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Bean
    @Profile("prod")
    public Person getProd() {
        return new Person("prod");
    }

    @Bean
    @Profile("dev")
    public Person getDev() {
        return new Person("dev");
    }
}
