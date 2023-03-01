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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LostUpdateTest {

    @Autowired
    private IsolationController isolationController;

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void afterEach() {
        final var book = bookRepository.findById(0).orElseThrow();
        System.out.println(book);
    }

    @Test
    @Sql("classpath:test-schema.sql")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void testSerializable() throws InterruptedException {
        action();
    }

    @Test
    @Sql("classpath:test-schema.sql")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void testRepeatableRead() throws InterruptedException {
        action();
    }

    @Test
    @Sql("classpath:test-schema.sql")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void testReadCommitted() throws InterruptedException {
        action();
    }

    @Test
    @Sql("classpath:test-schema.sql")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void testReadUncommitted() throws InterruptedException {
        action();
    }

    public void action() throws InterruptedException {
        final var start = new CountDownLatch(2);
        final var inter = new CountDownLatch(1);

        isolationController.lostUpdate1(start, inter, "FIRST");
        isolationController.lostUpdate2(start, inter, "SECOND");

        isolationController.getFirst();
    }
}
