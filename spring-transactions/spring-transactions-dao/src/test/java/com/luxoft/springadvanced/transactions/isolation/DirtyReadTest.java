package com.luxoft.springadvanced.transactions.isolation;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DirtyReadTest {

    @Autowired
    private IsolationController isolationController;
    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void afterEach() {
        System.out.println("All");
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Sql("classpath:clean-schema.sql")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void testSerializable() throws InterruptedException {
        action(0, 0);
    }

    @Test
    @Sql("classpath:clean-schema.sql")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void testRepeatableRead() throws InterruptedException {
        action(0, 0);
    }

    @Test
    @Sql("classpath:clean-schema.sql")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void testReadCommitted() throws InterruptedException {
        action(0, 0);
    }

    @Test
    @Sql("classpath:clean-schema.sql")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void testReadUncommitted() throws InterruptedException {
        action(0, 1);
    }

    public void action(long expectBefore, long expectAfter) throws InterruptedException {
        final var notify = new CountDownLatch(1);
        final var wait = new CountDownLatch(1);
        final var finish = new CountDownLatch(1);

        isolationController.addNewUncommitted(notify, wait, finish);

        final var before = bookRepository.countById(1);
        notify.countDown();

        wait.await();
        final var after = bookRepository.countById(1);
        System.out.println("Uncommitted");
        bookRepository.findAll().forEach(System.out::println);

        finish.countDown();
        assertAll(
                () -> assertEquals(expectBefore, before),
                () -> assertEquals(expectAfter, after)
        );
    }
}
