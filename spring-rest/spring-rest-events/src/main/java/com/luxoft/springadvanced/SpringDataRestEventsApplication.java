package com.luxoft.springadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(BaseConfig.class)
public class SpringDataRestEventsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataRestEventsApplication.class, args);
    }
}