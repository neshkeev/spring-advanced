package com.luxoft.springadvanced.springproxyaop.safestrings;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

@Aspect
@Component
public class SafeStringAspect {

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}
    @Pointcut("within(com.luxoft.springadvanced.springproxyaop..*)")
    public void local() {}
    @Pointcut("@annotation(SafeStrings)")
    public void safeStrings() {}
    @Pointcut("publicMethod() && local() && safeStrings()")
    public void localExecutionSafeStrings() {}

    @Around("localExecutionSafeStrings()")
    public Object replaceNullWithEmptyString(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs().length > 0
                ? amendArguments(joinPoint)
                : joinPoint.getArgs();
        return joinPoint.proceed(args);
    }

    private static Object[] amendArguments(ProceedingJoinPoint joinPoint) {
        final var parameterTypes = getParameterTypes(joinPoint);
        final Object[] initial = joinPoint.getArgs();

        return IntStream.range(0, initial.length)
                .mapToObj(i -> {
                    if (!String.class.isAssignableFrom(parameterTypes[i])) {
                        return initial[i];
                    }
                    return Objects.toString(initial[i], "");
                })
                .toArray();
    }

    private static Class<?>[] getParameterTypes(ProceedingJoinPoint joinPoint) {
        final Class<?> targetClass = joinPoint.getTarget().getClass();
        final var targetMethod = joinPoint.getSignature().getName();
        return Arrays.stream(targetClass.getDeclaredMethods())
                .filter(e -> e.getName().equals(targetMethod))
                .filter(e -> e.getParameterCount() == joinPoint.getArgs().length)
                .map(Method::getParameterTypes)
                .findFirst()
                .orElseThrow();
    }
}
