package com.luxoft.springadvanced.springtesting.annotations.hierarchy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextHierarchy(
        {
                @ContextConfiguration(classes = ContextHierarchyTest.ParentConfig.class),
                @ContextConfiguration(classes = ContextHierarchyTest.ChildConfig.class)
        }
)
public class ContextHierarchyTest {

    @Test
    public void test(@Autowired Namer person) {
        assertThat(person.getName(), is(equalTo("Child")));
    }

    @TestConfiguration
    static class ParentConfig {
        @Bean
        public Namer getNamer() {
            return new Namer.ParentNamer();
        }
    }

    @TestConfiguration
    static class ChildConfig {
        @Bean
        public Namer getNamer() {
            return new Namer.ChildNamer();
        }
    }
}
