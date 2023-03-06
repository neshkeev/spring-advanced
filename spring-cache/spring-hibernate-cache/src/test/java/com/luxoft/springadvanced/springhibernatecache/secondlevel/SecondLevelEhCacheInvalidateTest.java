package com.luxoft.springadvanced.springhibernatecache.secondlevel;

import com.luxoft.springadvanced.springhibernatecache.model.Department;
import com.luxoft.springadvanced.springhibernatecache.model.DepartmentRepository;
import com.luxoft.springadvanced.springhibernatecache.model.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "classpath:setup-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ContextConfiguration(classes = SecondLevelEhCacheInvalidateTest.SecondLevelEhCache.class, initializers = SecondLevelEhCacheInvalidateTest.SecondLevelEhCacheContextInitializer.class)
public class SecondLevelEhCacheInvalidateTest {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testAddNewEntry() {
        System.out.println("Before");
        departmentRepository.findAll();
        final var entity = new Department();
        departmentRepository.saveAndFlush(entity);

        System.out.println("after save");
        departmentRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional
    public void testUpdateEntryInPersistenceContext() {
        System.out.println("Before");
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        final var entity = employeeRepository.findById(1).get();
        entity.setName("John Silver");

        em.persist(entity);
        em.flush();

        final var session = em.unwrap(Session.class);
        session.clear();

        System.out.println("Second select");
        employeeRepository.findById(1).ifPresent(System.out::println);
    }

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void testUpdateEntryInQuery() {
        System.out.println("Before");
        departmentRepository.findById(1);
        em.createQuery("UPDATE Department d SET d.name = 'Marketing' WHERE d.id = 1").executeUpdate();
        em.flush();

        final var session = em.unwrap(Session.class);
        session.clear();

        System.out.println("Second select");
        departmentRepository.findById(1).ifPresent(System.out::println);
    }

    @SpringBootApplication
    @ComponentScan("com.luxoft.springadvanced.springhibernatecache")
    static class SecondLevelEhCache {
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
