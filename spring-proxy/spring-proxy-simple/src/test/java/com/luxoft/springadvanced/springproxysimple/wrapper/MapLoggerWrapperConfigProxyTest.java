package com.luxoft.springadvanced.springproxysimple.wrapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MapLoggerWrapperConfigProxyTest.MapLoggerWrapperConfig.class)
public class MapLoggerWrapperConfigProxyTest {
    @Autowired
    private Map<Integer, String> value;

    @BeforeEach
    public void beforeEach() {
        IntStream.range(1, 10)
                .forEach(i -> value.put(i, Integer.toString(i)));
    }

    @Test
    public void test() {
        final var keys = IntStream.range(1, 10).boxed().toArray();
        final var values = IntStream.range(1, 10).mapToObj(Integer::toString).toArray();

        assertAll(
                () -> assertThat(value.keySet(), containsInAnyOrder(keys)),
                () -> assertThat(value.values(), containsInAnyOrder(values))
        );
    }

    @TestConfiguration
    static class MapLoggerWrapperConfig {

        @Bean
        public Map<Integer, String> mapWithLogger() {
            //noinspection unchecked
            return (Map<Integer, String>) Proxy.newProxyInstance(
                    MapLoggerWrapperConfigProxyTest.class.getClassLoader(),
                    new Class[]{Map.class},
                    new MapLoggerWrapper<Integer, String>());
        }
    }
}
