package com.luxoft.springadvanced.springtesting.annotations.hierarchy;

import com.luxoft.springadvanced.springtesting.annotations.Person;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ImplicitParentAbstractTest.AbstractConfig.class)
public abstract class ImplicitParentAbstractTest {
    @TestConfiguration
    static class AbstractConfig {
        @Bean
        public Person janeDoe() {
            return new Person("Jane Doe");
        }
    }
}
