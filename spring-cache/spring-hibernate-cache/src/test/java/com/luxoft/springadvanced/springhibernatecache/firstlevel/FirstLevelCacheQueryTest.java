package com.luxoft.springadvanced.springhibernatecache.firstlevel;

import com.luxoft.springadvanced.springhibernatecache.model.Department;
import com.luxoft.springadvanced.springhibernatecache.model.DepartmentRepository;
import com.luxoft.springadvanced.springhibernatecache.model.Employee;
import com.luxoft.springadvanced.springhibernatecache.model.EmployeeRepository;
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
@Sql(value = "classpath:setup-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ContextConfiguration(classes = FirstLevelCacheQueryTest.FirstLevelCacheConfig.class)
public class FirstLevelCacheQueryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void testShareFirstLevelCache() {
        employeeRepository.findById(1);
        em.find(Employee.class, 1);
    }

    @Test
    @Transactional
    public void testPersistenceContextAndQuery() {
        departmentRepository.findById(1);
        em.find(Department.class, 1);
        em.createQuery("SELECT d FROM Department d WHERE d.id = 1").getSingleResult();
    }

    @SpringBootApplication
    @ComponentScan("com.luxoft.springadvanced.springhibernatecache")
    static class FirstLevelCacheConfig {
    }
}
