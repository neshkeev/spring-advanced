package com.luxoft.springadvanced.transactions.isolation;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PhantomReadTest {

    @Autowired
    private IsolationController isolationController;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void testSerializable() throws InterruptedException {
        action();
    }

    @Test
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void testRepeatableRead() throws InterruptedException {
        action();
    }

    @Test
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void testReadCommitted() throws InterruptedException {
        action();
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void testReadUncommitted() throws InterruptedException {
        action();
    }

    public void action() throws InterruptedException {
        isolationController.createFirstBook();

        final var notify = new CountDownLatch(1);
        final var wait = new CountDownLatch(1);

        isolationController.addNewCommitted(notify, wait);

        final var before = bookRepository.findAll();
        notify.countDown();
        System.out.println("Before adding new book");
        before.forEach(System.out::println);

        wait.await();
        final var after = bookRepository.findAll();
        System.out.println("After adding new book");
        after.forEach(System.out::println);
    }
}
