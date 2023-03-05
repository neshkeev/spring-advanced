package com.luxoft.springadvanced.springtesting.annotations.transactions;

import com.luxoft.springadvanced.springtesting.annotations.BaseConfig;
import com.luxoft.springadvanced.springtesting.annotations.Person;
import com.luxoft.springadvanced.springtesting.annotations.PersonRepository;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Rollback(false)
@DirtiesContext
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BaseConfig.class)
public class NoRollbackAfterTransactionTest {

    @Autowired
    private PersonRepository personRepository;
    private List<Matcher<Person>> before;

    @BeforeTransaction
    void beforeTransaction() {
        before = personRepository.findAll()
                .stream()
                .map(Matchers::equalTo)
                .collect(Collectors.toList());
    }

    @AfterTransaction
    void afterTransaction() {
        final var after = personRepository.findAll();
        final Matcher<? super List<Person>> matcher = before.isEmpty()
                ? is(not(empty()))
                : not(containsInAnyOrder(before));
        assertThat(after, matcher);
    }

    @Test
    @Transactional
    public void test() {
        final var entity = new Person("John");
        personRepository.saveAndFlush(entity);
    }
}
