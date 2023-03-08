package com.luxoft.springadvanced.springproxysimple.constant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Proxy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UltimateAnswerProxyTest.UltimateAnswerConfig.class)
public class UltimateAnswerProxyTest {

    @Test
    public void test(@Autowired CharSequence value) {
        assertThat(value.toString(), is(equalTo(UltimateAnswerInvocationHandler.ULTIMATE_ANSWER)));
    }

    @TestConfiguration
    static class UltimateAnswerConfig {

        @Bean
        public CharSequence getUltimateAnswer() {
            return (CharSequence) Proxy.newProxyInstance(UltimateAnswerProxyTest.class.getClassLoader(),
                    new Class[]{CharSequence.class},
                    new UltimateAnswerInvocationHandler());
        }
    }
}
