package com.luxoft.springadvanced.springadminclient.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@EnableScheduling
@Configuration
public class DemoScheduler {

    System.Logger logger = System.getLogger(DemoScheduler.class.getName());
    AtomicInteger counter = new AtomicInteger();

    @Scheduled(fixedRate = 10000)
    public void someScheduler() {
        int i = counter.getAndIncrement();
        logger.log(System.Logger.Level.DEBUG, i+" Debug level message");
        logger.log(System.Logger.Level.WARNING, i+" Warning level message");
        logger.log(System.Logger.Level.INFO, i+" Info level message");
    }

}
