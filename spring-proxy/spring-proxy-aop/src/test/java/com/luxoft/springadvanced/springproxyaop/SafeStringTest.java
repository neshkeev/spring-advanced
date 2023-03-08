package com.luxoft.springadvanced.springproxyaop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class SafeStringTest {

    @Autowired
    private SafeStringService safeStringService;

    @Test
    public void testNormalStringSafeLength() {
        final var length = safeStringService.safeLength("Hello, World!");
        assertThat(length, is(equalTo(13)));
    }

    @Test
    public void testNormalSafeStringLength() {
        final var length = safeStringService.length("Hello, World!");
        assertThat(length, is(equalTo(13)));
    }

    @Test
    public void testSafeLength() {
        //noinspection ConstantConditions
        final var length = safeStringService.safeLength(null);
        assertThat(length, is(equalTo(0)));
    }

    @Test
    public void testLength() {
        //noinspection ConstantConditions
        var ignore = assertThrows(NullPointerException.class, () -> safeStringService.length(null) );
    }

    @TestConfiguration
    @ComponentScan("com.luxoft.springadvanced.springproxyaop.safestrings")
    @EnableAspectJAutoProxy
    static class SafeStringConfig {
        @Bean
        public SafeStringService customService() {
            return new SafeStringService();
        }
    }
}
