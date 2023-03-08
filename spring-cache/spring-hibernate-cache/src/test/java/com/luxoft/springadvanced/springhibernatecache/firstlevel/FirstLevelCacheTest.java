package com.luxoft.springadvanced.springhibernatecache.firstlevel;

import com.luxoft.springadvanced.springhibernatecache.model.DepartmentRepository;
import com.luxoft.springadvanced.springhibernatecache.model.EmployeeRepository;
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
@ContextConfiguration(classes = FirstLevelCacheTest.FirstLevelCacheConfig.class)
public class FirstLevelCacheTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testNoTransactionalNoCache() {
        System.out.println("before");
        employeeRepository.findById(1);
        employeeRepository.findById(1);
        employeeRepository.findById(1);
        employeeRepository.findById(1);
        System.out.println("after");
    }

    @Test
    @Transactional
    public void testCacheInTransaction() {
        System.out.println("before");
        departmentRepository.findById(1);
        departmentRepository.findById(1);
        departmentRepository.findById(1);
        departmentRepository.findById(1);
        System.out.println("after");
    }

    @SpringBootApplication
    @ComponentScan("com.luxoft.springadvanced.springhibernatecache")
    static class FirstLevelCacheConfig {
    }
}
