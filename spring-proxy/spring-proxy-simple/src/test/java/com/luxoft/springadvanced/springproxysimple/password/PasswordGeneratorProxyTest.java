package com.luxoft.springadvanced.springproxysimple.password;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Proxy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PasswordGeneratorProxyTest.PasswordGeneratorConfig.class)
public class PasswordGeneratorProxyTest {

    @Test
    public void testPasswordFirst(@Autowired PasswordGenerator value) {
        System.out.println(value.getPassword());
    }

    @Test
    public void testPasswordSecond(@Autowired PasswordGenerator value) {
        System.out.println(value.getPassword());
    }

    @TestConfiguration
    static class PasswordGeneratorConfig {

        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public PasswordGenerator getPasswordGenerator() {
            return (PasswordGenerator) Proxy.newProxyInstance(PasswordGeneratorProxyTest.class.getClassLoader(),
                    new Class[]{PasswordGenerator.class},
                    new PasswordGeneratorInvocationHandler(32));
        }
    }
}
