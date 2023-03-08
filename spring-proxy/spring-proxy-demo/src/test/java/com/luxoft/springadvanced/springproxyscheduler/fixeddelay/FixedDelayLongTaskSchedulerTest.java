package com.luxoft.springadvanced.springproxyscheduler.fixeddelay;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FixedDelayLongTaskSchedulerTest.FixedDelaySchedulerConfig.class)
public class FixedDelayLongTaskSchedulerTest {

    @Autowired
    private CountDownLatch latch;

    @Autowired
    private AtomicInteger inc;

    @Test
    public void test() throws InterruptedException {
        latch.await();
        final var before = inc.get();
        Thread.sleep(350);
        final var after = inc.get();
        assertThat(before + 1, is(equalTo(after)));
    }

    @Configuration
    @EnableScheduling
    static class FixedDelaySchedulerConfig {

        @Bean
        public CountDownLatch latch() {
            return new CountDownLatch(1);
        }

        @Bean
        public FixedDelayLongTaskScheduler scheduler() {
            return new FixedDelayLongTaskScheduler();
        }

        @Bean
        public AtomicInteger increment() {
            return new AtomicInteger(0);
        }
    }
}
