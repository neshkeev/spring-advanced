package com.luxoft.springadvanced.springtesting.annotations.listeners;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

@SuppressWarnings("NullableProblems")
public class CustomListener implements TestExecutionListener {

    @Autowired
    private Logger log;

    @Override
    public void beforeTestClass(TestContext testContext) {
        testContext.getApplicationContext()
                .getAutowireCapableBeanFactory()
                .autowireBean(this);
        log.info("beforeTestClass");
    }

    @Override
    public void prepareTestInstance(TestContext testContext) {
        log.info("prepareTestInstance");
    }

    @Override
    public void beforeTestMethod(TestContext testContext) {
        log.info("beforeTestMethod");
    }

    @Override
    public void beforeTestExecution(TestContext testContext) {
        log.info("beforeTestExecution");
    }

    @Override
    public void afterTestExecution(TestContext testContext) {
        log.info("afterTestExecution");
    }

    @Override
    public void afterTestMethod(TestContext testContext) {
        log.info("afterTestMethod");
    }

    @Override
    public void afterTestClass(TestContext testContext) {
        log.info("afterTestClass" + testContext);
    }
}
