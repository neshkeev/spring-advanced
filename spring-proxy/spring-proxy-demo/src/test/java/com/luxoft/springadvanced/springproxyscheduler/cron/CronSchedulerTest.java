package com.luxoft.springadvanced.springproxyscheduler.cron;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CronSchedulerTest.SchedulerConfig.class)
public class CronSchedulerTest {

    @Autowired
    private AtomicInteger inc;

    @Test
    public void test() throws InterruptedException {
        final var before = inc.get();
        Thread.sleep(1000);
        final var after = inc.get();
        assertThat(before + 1, is(equalTo(after)));
    }

    @TestConfiguration
    @EnableScheduling
    static class SchedulerConfig {
        @Bean
        public CronScheduler cronScheduler() {
            return new CronScheduler();
        }

        @Bean
        public AtomicInteger increment() {
            return new AtomicInteger(0);
        }
    }
}
