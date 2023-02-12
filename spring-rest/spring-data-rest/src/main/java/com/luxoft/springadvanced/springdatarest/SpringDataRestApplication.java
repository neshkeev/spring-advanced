package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.BaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(BaseConfig.class)
public class SpringDataRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataRestApplication.class, args);
    }
}
