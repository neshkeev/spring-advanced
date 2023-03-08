package com.luxoft.springadvanced.springproxyscheduler.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CronScheduler {

    @Autowired
    private AtomicInteger inc;

    @Scheduled(cron = "*/1 * * * * ?")
    public void execute() {
        inc.incrementAndGet();
    }
}
