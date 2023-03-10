package com.luxoft.springadvanced.springactuatorsimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringActuatorApplication.class, args);
    }
}
