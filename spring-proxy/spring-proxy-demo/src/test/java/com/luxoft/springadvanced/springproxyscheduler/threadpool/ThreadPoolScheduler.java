package com.luxoft.springadvanced.springproxyscheduler.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class ThreadPoolScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolScheduler.class);

    @Scheduled(fixedRate = 100)
    public void fixedRate() throws InterruptedException {
        LOG.info("[{}] rate", Thread.currentThread().getName());
        Thread.sleep(200);
    }

    @Scheduled(fixedDelay = 100)
    public void fixedDelay() throws InterruptedException {
        LOG.info("[{}] delay", Thread.currentThread().getName());
        Thread.sleep(100);
    }
}
