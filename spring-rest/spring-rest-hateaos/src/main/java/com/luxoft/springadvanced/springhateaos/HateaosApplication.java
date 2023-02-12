package com.luxoft.springadvanced.springhateaos;

import com.luxoft.springadvanced.BaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(BaseConfig.class)
public class HateaosApplication {
    public static void main(String[] args) {
        SpringApplication.run(HateaosApplication.class, args);
    }
}
