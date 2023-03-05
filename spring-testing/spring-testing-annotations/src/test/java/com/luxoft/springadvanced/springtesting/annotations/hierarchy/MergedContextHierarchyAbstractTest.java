package com.luxoft.springadvanced.springtesting.annotations.hierarchy;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration(name = "parent", classes = MergedContextHierarchyAbstractTest.ParentMergedConfig.class),
        @ContextConfiguration(name = "child", classes = MergedContextHierarchyAbstractTest.ChildMergedConfig.class)
})
public class MergedContextHierarchyAbstractTest {
    @TestConfiguration
    abstract static class ParentMergedConfig {
        @Bean("parent")
        public Namer parent() {
            return new Namer.ParentNamer();
        }
    }
    @TestConfiguration
    abstract static class ChildMergedConfig {
        @Bean("child")
        public Namer child() {
            return new Namer.ChildNamer();
        }
    }
}