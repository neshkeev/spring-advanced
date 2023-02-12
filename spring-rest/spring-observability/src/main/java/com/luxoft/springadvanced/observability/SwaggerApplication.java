package com.luxoft.springadvanced.observability;

import com.luxoft.springadvanced.BaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

// http://localhost:8080/swagger-ui/index.html
@Import(BaseConfig.class)
public class SwaggerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }
}
