package com.luxoft.springadvanced.transactions.isolation;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NonRepeatableReadsTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    IsolationController isolationController;
    @Autowired
    BookRepository bookRepository;

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
        final var notify = new CountDownLatch(1);
        final var wait = new CountDownLatch(1);

        isolationController.updateFirst(notify, wait);

        final var before = bookRepository.findById(0).orElseThrow();

        System.out.println(before.getTitle());
        notify.countDown();

        wait.await();
        System.out.println("Before second");
        final var after = bookRepository.findById(0).orElseThrow();
        em.refresh(after);
        System.out.println(after.getTitle());
    }
}
