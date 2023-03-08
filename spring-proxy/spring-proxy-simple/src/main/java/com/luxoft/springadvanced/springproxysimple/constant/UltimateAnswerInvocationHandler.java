package com.luxoft.springadvanced.springproxysimple.constant;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UltimateAnswerInvocationHandler implements InvocationHandler {

    public static final String ULTIMATE_ANSWER = Integer.toString(42);

    @SuppressWarnings("SuspiciousInvocationHandlerImplementation")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return ULTIMATE_ANSWER;
    }
}
