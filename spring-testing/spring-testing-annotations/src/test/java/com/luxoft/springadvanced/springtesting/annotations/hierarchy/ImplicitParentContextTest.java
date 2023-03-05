package com.luxoft.springadvanced.springtesting.annotations.hierarchy;

import com.luxoft.springadvanced.springtesting.annotations.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ContextHierarchy(@ContextConfiguration(classes = ImplicitParentContextTest.MyConfig.class))
public class ImplicitParentContextTest extends ImplicitParentAbstractTest {
    @Autowired
    private Person person;

    @Test
    public void test() {
        assertThat(person.getName(), is(equalTo("Jane Doe")));
    }

    @TestConfiguration
    static class MyConfig {}
}
