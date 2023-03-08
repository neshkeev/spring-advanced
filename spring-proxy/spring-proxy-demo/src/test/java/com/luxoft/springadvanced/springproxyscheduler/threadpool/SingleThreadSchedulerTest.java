package com.luxoft.springadvanced.springproxyscheduler.threadpool;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SingleThreadSchedulerTest.FixedRateSchedulerConfig.class)
public class SingleThreadSchedulerTest {

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Configuration
    @EnableScheduling
    static class FixedRateSchedulerConfig {
        @Bean
        public SingleThreadScheduler scheduler() {
            return new SingleThreadScheduler();
        }

    }
}
