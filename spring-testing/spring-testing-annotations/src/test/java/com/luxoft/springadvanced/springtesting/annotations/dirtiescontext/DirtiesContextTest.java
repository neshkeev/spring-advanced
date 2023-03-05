package com.luxoft.springadvanced.springtesting.annotations.dirtiescontext;

import com.luxoft.springadvanced.springtesting.annotations.BaseConfig;
import com.luxoft.springadvanced.springtesting.annotations.Person;
import com.luxoft.springadvanced.springtesting.annotations.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@Rollback(false)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BaseConfig.class)
public class DirtiesContextTest {

    @Test
    @DirtiesContext
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void test(@Autowired PersonRepository personRepository) {
        checkDB(personRepository);
    }

    @Test
    @DirtiesContext
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void testEmpty(@Autowired PersonRepository personRepository) {
        checkDB(personRepository);
    }

    private static void checkDB(PersonRepository personRepository) {
        final var all = personRepository.findAll();

        final var entity = new Person("John");
        personRepository.saveAndFlush(entity);

        assertAll(
                () -> assertThat(all, is(empty())),
                () -> assertThat(personRepository.findAll(), is(not(empty())))
        );
    }
}
