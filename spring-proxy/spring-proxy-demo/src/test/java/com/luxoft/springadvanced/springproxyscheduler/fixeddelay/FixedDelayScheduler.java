package com.luxoft.springadvanced.springproxyscheduler.fixeddelay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedDelayScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(FixedDelayScheduler.class);

    @Autowired
    private CountDownLatch latch;

    @Autowired
    private AtomicInteger inc;

    @Scheduled(fixedDelay = 100)
    public void execute() {
        LOG.info("{}", Thread.currentThread().getName());
        inc.incrementAndGet();
        latch.countDown();
    }
}
