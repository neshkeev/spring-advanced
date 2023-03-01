package com.luxoft.springadvanced.transactions.nestedornew;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import com.luxoft.springadvanced.transactions.domain.Book;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.NestedTransactionNotSupportedException;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class NestedOrRequiresNewTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private NestedOrRequiresNewController controller;

    @BeforeEach
    public void beforeEach() {
        bookRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testRequiresNew() throws InterruptedException {
        bookRepository.save(new Book("Alice in Wonderland", null));
        controller.requiresNew();

        final var checkDone = new CountDownLatch(1);
        System.out.println("List of saved books for Requires New");
        controller.check(checkDone);
        checkDone.await();
    }

    @Test
    @Transactional
    public void testNested() throws InterruptedException {
        bookRepository.save(new Book("Alice in Wonderland", null));
        // Nested is not supported for H2
        assertThrows(NestedTransactionNotSupportedException.class, controller::nested);

        final var checkDone = new CountDownLatch(1);
        System.out.println("List of saved books for Nested");
        controller.check(checkDone);
        checkDone.await();
    }

}
