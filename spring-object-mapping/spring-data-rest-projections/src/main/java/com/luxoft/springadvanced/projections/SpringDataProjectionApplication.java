package com.luxoft.springadvanced.projections;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataProjectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataProjectionApplication.class, args);
    }

    private final CustomerRepository customers;

    public SpringDataProjectionApplication(CustomerRepository customers) {
        this.customers = customers;
    }

    @PostConstruct
    public void init() {
        customers.save(new Customer("John", "Smith", Customer.Gender.MALE,
                new Address("3 Flowers Streets", "12345", "Boston", "MA")));

    }
}
