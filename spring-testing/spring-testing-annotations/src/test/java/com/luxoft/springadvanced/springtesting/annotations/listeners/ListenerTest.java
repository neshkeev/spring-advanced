package com.luxoft.springadvanced.springtesting.annotations.listeners;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestExecutionListeners(CustomListener.class)
@ContextConfiguration(classes = ListenerTest.ListenerConfig.class)
public class ListenerTest {
    private static final Logger LOG = LoggerFactory.getLogger(ListenerTest.class);

    @Test
    public void test() {
    }

    @TestConfiguration
    static class ListenerConfig {
        @Bean
        public Logger getLogger() {
            return LOG;
        }
    }
}
