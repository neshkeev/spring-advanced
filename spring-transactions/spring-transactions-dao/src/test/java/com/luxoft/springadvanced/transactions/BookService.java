package com.luxoft.springadvanced.transactions;

import com.luxoft.springadvanced.transactions.domain.Book;
import com.luxoft.springadvanced.transactions.domain.Log;
import com.luxoft.springadvanced.transactions.repositories.BookDao;
import com.luxoft.springadvanced.transactions.repositories.LogDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private final BookDao bookDao;
    private final LogDao logDao;

    public BookService(BookDao bookDao, LogDao logDao) {
        this.bookDao = bookDao;
        this.logDao = logDao;
    }

    @Transactional
    // if actually transactional, should rollback adding book and log (because of exception)
    public void addBookAndLog() {
        Book book = new Book("Java", null);
        bookDao.save(book);
        System.out.println("********** Book added **********");
        // add book
        //noinspection ConstantConditions
        if (true) throw new RuntimeException();
        // add log
        Log log = new Log("new message");
        logDao.save(log);
        System.out.println("********** Log added **********");
    }

    static class SomeException extends RuntimeException {}

    @Transactional(rollbackFor = SomeException.class)
    public void transactionWithException() {
        throw new SomeException();
    }

    @Transactional(rollbackFor = SomeException.class)
    public void transactionWithNoException() {
        try {
            throw new SomeException();
        }
        catch (SomeException ignore) {}
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addBookRequiresNew(String title) {
        bookDao.save(new Book(title, null));
    }

    @Transactional(propagation = Propagation.NESTED)
    public void addBookNested(String title) {
        bookDao.save(new Book(title, null));
    }

}
