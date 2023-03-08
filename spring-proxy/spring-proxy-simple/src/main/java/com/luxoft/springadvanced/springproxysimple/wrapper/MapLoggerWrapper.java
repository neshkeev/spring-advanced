package com.luxoft.springadvanced.springproxysimple.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MapLoggerWrapper<T, U> implements InvocationHandler {

    private final static Logger LOG = LoggerFactory.getLogger(MapLoggerWrapper.class);

    private final Map<T, U> container;

    public MapLoggerWrapper() {
        this.container = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOG.info("Start executing {}", method.getName());
        final var start = LocalDateTime.now();
        try {
            return method.invoke(container, args);
        } finally {
            final var end = LocalDateTime.now();
            LOG.info("End executing {} which took {}ns", method.getName(), Duration.between(start, end).toNanos());
        }
    }
}
