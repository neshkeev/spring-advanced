package com.luxoft.springadvanced.transactions.isolation;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TransactionIsolationTest {

    @Autowired
    IsolationController isolationController; @Autowired BookRepository bookRepository;

    @BeforeEach
    public void beforeEach() {
        bookRepository.deleteAll();
    }

    @Test
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void testSerializable() throws InterruptedException {
        final var notify = new CountDownLatch(1);
        final var wait = new CountDownLatch(1);
        isolationController.updateFirst(notify, wait);
        System.out.println("Before");
        bookRepository.findAll().forEach(System.out::println);
        notify.countDown();
        wait.await();
        System.out.println("After");
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void testRepeatableRead() throws InterruptedException {
        final var notify = new CountDownLatch(1);
        final var wait = new CountDownLatch(1);
        isolationController.updateFirst(notify, wait);
        System.out.println("Before");
        bookRepository.findAll().forEach(System.out::println);
        notify.countDown();
        wait.await();
        System.out.println("After");
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void testReadCommitted() throws InterruptedException {
        final var notify = new CountDownLatch(1);
        final var wait = new CountDownLatch(1);
        isolationController.updateFirst(notify, wait);
        System.out.println("Before");
        bookRepository.findAll().forEach(System.out::println);
        notify.countDown();
        wait.await();
        System.out.println("After");
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void testReadCommitted1() throws InterruptedException {
        final var notify = new CountDownLatch(1);
        final var wait = new CountDownLatch(1);
        final var finish = new CountDownLatch(1);
        isolationController.addNewUncommitted(notify, wait, finish);
        System.out.println("Before");
        bookRepository.findById(1).ifPresent(System.out::println);
        notify.countDown();
        wait.await();
        System.out.println("After");
        bookRepository.findById(1).ifPresent(System.out::println);
        System.out.println(Thread.currentThread().getName() + "Done");
        finish.countDown();
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void testReadUncommitted() throws InterruptedException {
        final var notify = new CountDownLatch(1);
        final var wait = new CountDownLatch(1);
        final var finish = new CountDownLatch(1);
        isolationController.addNewUncommitted(notify, wait, finish);
        System.out.println("Before");
        bookRepository.findAll().forEach(System.out::println);
        notify.countDown();
        wait.await();
        System.out.println("After");
        bookRepository.findAll().forEach(System.out::println);
        finish.countDown();
    }
}
