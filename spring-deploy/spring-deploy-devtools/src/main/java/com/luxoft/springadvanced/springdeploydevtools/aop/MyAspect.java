package com.luxoft.springadvanced.springdeploydevtools.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}
    @Pointcut("within(com.luxoft.springadvanced.springdeploydevtools.aop..*)")
    public void local() {}
    @Pointcut("publicMethod() && local()")
    public void localCode() {}

    @Around("localCode()")
    public Object replaceNullWithEmptyString(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before " + joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }
}
