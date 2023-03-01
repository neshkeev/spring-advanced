package com.luxoft.springadvanced.transactions.nestedornew;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import com.luxoft.springadvanced.transactions.domain.Book;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

@Controller
public class NestedOrRequiresNewController {
    private final BookRepository bookRepository;

    public NestedOrRequiresNewController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested() {
        final var book = new Book("Through the Looking-Glass", null);
        bookRepository.save(book);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew() {
        final var book = new Book("Through the Looking-Glass", null);
        bookRepository.save(book);
    }

    @Async("nested")
    public void check(CountDownLatch start) {
        try {
            bookRepository.findAll().forEach(System.out::println);
        } finally {
            start.countDown();
        }
    }
}
