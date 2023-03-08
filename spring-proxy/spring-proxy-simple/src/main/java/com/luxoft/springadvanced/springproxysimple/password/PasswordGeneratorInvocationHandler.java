package com.luxoft.springadvanced.springproxysimple.password;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class PasswordGeneratorInvocationHandler implements InvocationHandler {

    private final String password;

    private static final int[] ALPHABET;

    static {
        final var lowerCase = IntStream.rangeClosed(0, 'z' - 'a').map(i -> 'a' + i);
        final var upperCase = IntStream.rangeClosed(0, 'Z' - 'A').map(i -> 'A' + i);
        final var numbers = IntStream.rangeClosed(0, '9' - '0').map(i -> '0' + i);

        ALPHABET = IntStream.concat(lowerCase, IntStream.concat(upperCase, numbers))
                .toArray();
    }

    public PasswordGeneratorInvocationHandler(int size) {
        final var random = new Random();
        this.password = IntStream.range(0, size)
                .map(__ -> random.nextInt(ALPHABET.length))
                .map(e -> ALPHABET[e])
                .mapToObj(Character::toString)
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append
                ))
                .toString();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getPassword".equals(method.getName())) return password;

        return method.invoke(password, args);
    }
}
