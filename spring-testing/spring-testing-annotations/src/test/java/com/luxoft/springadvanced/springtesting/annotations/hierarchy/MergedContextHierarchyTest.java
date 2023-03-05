package com.luxoft.springadvanced.springtesting.annotations.hierarchy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@ContextHierarchy({
        @ContextConfiguration(name = "child", classes = MergedContextHierarchyTest.ChildMergedConfig.class)
})
public class MergedContextHierarchyTest extends MergedContextHierarchyAbstractTest {
    @Autowired
    @Qualifier("heir")
    public Namer heirNamer;

    @Autowired
    @Qualifier("child")
    public Namer childNamer;

    @Autowired
    @Qualifier("parent")
    public Namer parent;

    @Test
    public void test() {
        assertAll(
                () -> assertThat(heirNamer.getName(), is(equalTo("Heir"))),
                () -> assertThat(childNamer.getName(), is(equalTo("Child"))),
                () -> assertThat(parent.getName(), is(equalTo("Parent")))
        );
    }

    @TestConfiguration
    abstract static class ChildMergedConfig {
        @Bean("heir")
        public Namer getInheritor() {
            return new Namer.HeirNamer();
        }
    }
}