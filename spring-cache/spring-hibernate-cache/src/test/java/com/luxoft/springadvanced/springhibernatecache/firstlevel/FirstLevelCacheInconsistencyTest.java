package com.luxoft.springadvanced.springhibernatecache.firstlevel;

import com.luxoft.springadvanced.springhibernatecache.model.Department;
import com.luxoft.springadvanced.springhibernatecache.model.DepartmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@Sql("classpath:setup-schema.sql")
@ContextConfiguration(classes = FirstLevelCacheInconsistencyTest.FirstLevelCacheConfig.class)
public class FirstLevelCacheInconsistencyTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void testUpdatePersistentContext() {
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        final var dept = departmentRepository.findById(1).get();
        System.out.println(dept.getName());
        dept.setName("Marketing");
        departmentRepository.save(dept);
        em.refresh(dept);
        System.out.println(dept.getName());
    }

    @Test
    @Transactional
    public void testUpdateQuery() {
        final var dept = em.find(Department.class, 1);
        System.out.println(dept);
        // https://jakarta.ee/specifications/persistence/3.0/jakarta-persistence-spec-3.0.html#a5636
        em.createQuery("update Department d set d.executives = true")
                .executeUpdate();
        dept.setName("Marketing");
        em.flush();
        em.refresh(dept);
        System.out.println(dept);
    }

    @SpringBootApplication
    @ComponentScan("com.luxoft.springadvanced.springhibernatecache")
    static class FirstLevelCacheConfig {
    }
}
