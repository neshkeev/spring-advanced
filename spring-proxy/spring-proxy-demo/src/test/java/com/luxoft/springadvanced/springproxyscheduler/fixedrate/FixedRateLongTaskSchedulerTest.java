package com.luxoft.springadvanced.springproxyscheduler.fixedrate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FixedRateLongTaskSchedulerTest.FixedRateSchedulerConfig.class)
public class FixedRateLongTaskSchedulerTest {

    @Autowired
    private CountDownLatch latch;

    @Test
    public void test() throws InterruptedException {
        latch.await();
        Thread.sleep(350);
    }

    @Configuration
    @EnableScheduling
    static class FixedRateSchedulerConfig {
        @Bean
        public CountDownLatch latch() {
            return new CountDownLatch(1);
        }

        @Bean
        public FixedRateLongTaskScheduler scheduler() {
            return new FixedRateLongTaskScheduler();
        }

        @Bean
        public AtomicInteger increment() {
            return new AtomicInteger(0);
        }
    }
}
