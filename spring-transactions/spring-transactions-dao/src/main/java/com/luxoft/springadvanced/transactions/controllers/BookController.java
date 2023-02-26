package com.luxoft.springadvanced.transactions.controllers;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import com.luxoft.springadvanced.transactions.dao.LoggerRepository;
import com.luxoft.springadvanced.transactions.domain.Book;
import com.luxoft.springadvanced.transactions.domain.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class BookController {
    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;
    private final LoggerRepository loggerRepository;
    private final BookController bookController;

    public BookController(BookRepository bookRepository, LoggerRepository loggerRepository, @Lazy BookController bookController) {
        this.bookRepository = bookRepository;
        this.loggerRepository = loggerRepository;
        this.bookController = bookController;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBook(Book book) {
        loggerRepository.save(new Log(book.getTitle() + " is being saved"));
        bookRepository.save(book);
        loggerRepository.save(new Log(book.getTitle() + " saved"));
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = BookWithNoTitleException.class)
//    @Transactional(propagation = Propagation.SUPPORTS)
    public void isValidBook(Book book) {
        if (!StringUtils.hasText(book.getTitle())) {
            throw new BookWithNoTitleException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateBook(Book book) {
        bookRepository.save(book);
        loggerRepository.save(new Log(book.getTitle() + " updated"));
    }

    @Transactional(propagation = Propagation.NEVER)
    public void greeting() {
        LOG.info("Greeting");
    }

//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void throwException() throws IOException {
        //noinspection resource
        new FileInputStream("/don/t/exist");
    }

    @PostMapping("/book")
//    @Transactional(rollbackFor = FileNotFoundException.class)
    @Transactional
    public void testSaveBook(@RequestBody Book book) {
        bookRepository.save(new Book("hello", null));
        try {
            bookController.throwException();
//            bookController.isValidBook(new Book());
        }
        catch (Exception ignore) {}
        finally {
//            bookRepository.findAll().forEach(System.out::println);
            bookController.listBooks();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void listBooks() {
        bookRepository.findAll().forEach(System.out::println);
    }
}
