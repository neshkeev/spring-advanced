package com.luxoft.springadvanced.springproxyscheduler.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public class AsyncRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncRunner.class);

    @Async("concurrent")
    public void concurrent() {
        LOG.info("[{}] async task", Thread.currentThread().getName());
    }

    @Async("concurrent")
    public CompletableFuture<String> message() {
        LOG.info("[{}] async task", Thread.currentThread().getName());
        return CompletableFuture.completedFuture("Hello, World");
    }
}
