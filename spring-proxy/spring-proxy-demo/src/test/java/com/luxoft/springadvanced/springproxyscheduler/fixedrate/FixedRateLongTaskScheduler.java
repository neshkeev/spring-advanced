package com.luxoft.springadvanced.springproxyscheduler.fixedrate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedRateLongTaskScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(FixedRateLongTaskScheduler.class);

    @Autowired
    private CountDownLatch latch;
    @Autowired
    private AtomicInteger inc;

    @Scheduled(fixedRate = 100, initialDelay = 100)
    public void execute() throws InterruptedException {
        LOG.info("rate {}", Thread.currentThread().getName());
        inc.incrementAndGet();
        latch.countDown();
        Thread.sleep(200);
    }
}
