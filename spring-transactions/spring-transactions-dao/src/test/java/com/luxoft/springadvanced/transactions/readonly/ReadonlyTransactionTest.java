package com.luxoft.springadvanced.transactions.readonly;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import com.luxoft.springadvanced.transactions.dao.ReviewRepository;
import com.luxoft.springadvanced.transactions.domain.Book;
import com.luxoft.springadvanced.transactions.domain.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
public class ReadonlyTransactionTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void beforeEach() {
        reviewRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @AfterEach
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void afterEach() {
        reviewRepository.findAll().forEach(System.out::println);
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional(readOnly = true)
    public void test() {
        final var review = new Review("Hello, World!");
        final var book = new Book("Alice in Wonderland", null);

        Assertions.assertDoesNotThrow(() -> reviewRepository.save(review));
        Assertions.assertDoesNotThrow(() -> bookRepository.save(book));
    }
}
