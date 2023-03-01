package com.luxoft.springadvanced.transactions.rollbackorcommit;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

    private final DemoService demoService;

    public DemoService(@Lazy DemoService demoService) {
        this.demoService = demoService;
    }

    @Transactional
    public void throwsMyRuntimeException() {
        throw new MyRuntimeException();
    }

    @Transactional
    public void throwsMyCheckedException() throws MyCheckedException {
        throw new MyCheckedException();
    }

    @Transactional
    public void actionWithRuntimeException() {
        try {
            demoService.throwsMyRuntimeException();
        }
        catch (Exception ignore) {}
    }

    @Transactional
    public void actionWithCheckedException() {
        try {
            demoService.throwsMyCheckedException();
        }
        catch (Exception ignore) {}
    }

    public static final class MyRuntimeException extends RuntimeException {}
    public static final class MyCheckedException extends Exception {}
}
