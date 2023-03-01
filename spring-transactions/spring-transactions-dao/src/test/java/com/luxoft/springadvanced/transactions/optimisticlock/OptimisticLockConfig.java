package com.luxoft.springadvanced.transactions.optimisticlock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class OptimisticLockConfig {

    @Bean(name = "optimistic")
    public TaskExecutor executorA() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setThreadNamePrefix("optimistic");
        executor.initialize();
        return executor;
    }

}
