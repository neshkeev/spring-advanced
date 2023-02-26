package com.luxoft.springadvanced.transactions;

import com.luxoft.springadvanced.transactions.controllers.BookController;
import com.luxoft.springadvanced.transactions.dao.BookRepository;
import com.luxoft.springadvanced.transactions.dao.LoggerRepository;
import com.luxoft.springadvanced.transactions.domain.Book;
import com.luxoft.springadvanced.transactions.domain.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("JUnitMalformedDeclaration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LoggerRepository loggerRepository;

    @BeforeEach
    @Transactional
    public void beforeEach(@Autowired BookRepository bookRepository, @Autowired LoggerRepository loggerRepository) {
        System.out.println("Before");
        bookRepository.findAll().forEach(System.out::println);
        bookRepository.deleteAll();
        loggerRepository.deleteAll();
    }

    @AfterEach
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void afterEach(@Autowired BookController bookController) {
        System.out.println("After");
        bookController.listBooks();
//        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void testSimpleNever1(@Autowired BookController bookController) {
        bookController.greeting();
    }

    @Test
    @Transactional(rollbackFor = FileNotFoundException.class)
    public void testSaveBook(@Autowired BookController bookController) {
        bookRepository.save(new Book("hello", null));
        bookController.saveBook(getBook());
        try {
            bookController.isValidBook(new Book(null, null));
//        bookController.throwException();
        }
        catch (Exception ignore) {}
//        assertCount(1L, 2L);
    }

    @Test
    @Transactional
    public void testNeverWithTransaction(@Autowired BookController bookController) {
        assertThrows(IllegalTransactionStateException.class, bookController::greeting);
    }

    @Test
    @Transactional
    public void testSaveBook1(@Autowired BookController bookController) {
        bookController.saveBook(getBook());
        assertCount(1L, 2L);
    }

    @Test
    public void testNoSaveInReadOnly() {
        bookRepository.save(getBook());
        loggerRepository.save(new Log("Simple line"));

        assertCount(1L, 0L);
    }



    private static Book getBook() {
        return new Book("Alice in Wonderland", null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void assertCount(@SuppressWarnings("SameParameterValue") long booksTotal, long logsTotal) {
        assertAll(
                () -> assertThat(this.bookRepository.count(), is(equalTo(booksTotal))),
                () -> assertThat(this.loggerRepository.count(), is(equalTo(logsTotal)))
        );
    }
}
