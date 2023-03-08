package com.luxoft.springadvanced.springproxyscheduler.async;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AsyncTest.FixedRateSchedulerConfig.class)
public class AsyncTest {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncTest.class);

    @Test
    public void test(@Autowired AsyncRunner runner) {
        LOG.info("[{}] main", Thread.currentThread().getName());

        runner.concurrent();
        runner.message()
                .thenAccept(LOG::info)
                .join();
    }

    @Configuration
    @EnableAsync
    static class FixedRateSchedulerConfig {

        @Bean
        public AsyncRunner runner() {
            return new AsyncRunner();
        }

        @Bean(name = "concurrent")
        public TaskExecutor executor() {
            var executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(2);
            executor.setMaxPoolSize(2);
            executor.setThreadNamePrefix("concurrent");
            executor.initialize();
            return executor;
        }
    }
}
