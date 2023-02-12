package com.luxoft.springadvanced.springwebclient;

import com.luxoft.springadvanced.BaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(BaseConfig.class)
public class WebClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebClientApplication.class, args);
    }
}
