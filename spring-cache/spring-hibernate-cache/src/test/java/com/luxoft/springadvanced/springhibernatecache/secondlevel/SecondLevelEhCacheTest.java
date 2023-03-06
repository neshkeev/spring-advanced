package com.luxoft.springadvanced.springhibernatecache.secondlevel;

import com.luxoft.springadvanced.springhibernatecache.model.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "classpath:setup-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ContextConfiguration(classes = SecondLevelEhCacheTest.SecondLevelEhCache.class, initializers = SecondLevelEhCacheTest.SecondLevelEhCacheContextInitializer.class)
public class SecondLevelEhCacheTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SecondTransactionController controller;

    @Test
    public void testEhCacheNoTransaction() {
        departmentRepository.findById(1);
        System.out.println("Again");
        departmentRepository.findById(1);
    }

    @Test
    public void testSecondTransaction() throws InterruptedException {
        final var latch = new CountDownLatch(1);
        controller.select(latch);

        departmentRepository.findById(1);
        System.out.println("Again");
        departmentRepository.findById(1);

        latch.countDown();
    }

    @SpringBootApplication
    @EnableAsync
    @ComponentScan("com.luxoft.springadvanced.springhibernatecache")
    static class SecondLevelEhCache {

        @Bean(name = "concurrent")
        public TaskExecutor executor() {
            var executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(2);
            executor.setMaxPoolSize(2);
            executor.setThreadNamePrefix("concurrent");
            executor.initialize();
            return executor;
        }
    }

    static class SecondLevelEhCacheContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "hibernate.cache.use_second_level_cache=true",
                    "hibernate.cache.region.factory_class=org.hibernate.cache.jcache.internal.JCacheRegionFactory",
                    "hibernate.javax.cache.provider=org.ehcache.jsr107.EhcacheCachingProvider",
                    "hibernate.javax.cache.uri=classpath:jcache.xml"
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
