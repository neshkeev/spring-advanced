package com.luxoft.springadvanced.transactions.optimisticlock;

import com.luxoft.springadvanced.transactions.dao.ReviewRepository;
import com.luxoft.springadvanced.transactions.domain.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OptimisticLockTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private OptimisticLockController controller;

    @BeforeEach
    public void beforeEach() {
        reviewRepository.save(new Review("Hello, World!"));
    }

    @AfterEach
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void afterEach() {
        reviewRepository.findAll().forEach(System.out::println);
    }

    @Sql("classpath:clean-schema.sql")
    @Test
    public void test() throws InterruptedException {
        final Review review = reviewRepository.findById(1).orElseThrow();
        review.setText(review.getText().toLowerCase());
        System.out.println(review);

        final var wait = new CountDownLatch(1);
        final var notify = new CountDownLatch(1);
        controller.action(notify, wait);

        notify.countDown();

        wait.await();
        assertThrows(
                ObjectOptimisticLockingFailureException.class,
                () -> reviewRepository.save(review));

    }
}
