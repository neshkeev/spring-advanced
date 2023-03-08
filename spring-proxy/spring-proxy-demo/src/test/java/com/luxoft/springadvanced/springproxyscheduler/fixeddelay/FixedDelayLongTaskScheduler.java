package com.luxoft.springadvanced.springproxyscheduler.fixeddelay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedDelayLongTaskScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(FixedDelayLongTaskScheduler.class);

    @Autowired
    private CountDownLatch latch;

    @Autowired
    private AtomicInteger inc;

    @Scheduled(fixedDelay = 100, initialDelay = 100)
    public void execute() throws InterruptedException {
        LOG.info("{}", Thread.currentThread().getName());
        inc.incrementAndGet();
        latch.countDown();
        Thread.sleep(200);
    }
}
