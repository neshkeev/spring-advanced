package com.luxoft.springadvanced.transactions.distributed;

import com.luxoft.springadvanced.transactions.distributed.db1.BookDao;
import com.luxoft.springadvanced.transactions.distributed.db1.model.Book;
import com.luxoft.springadvanced.transactions.distributed.db2.Book2Dao;
import com.luxoft.springadvanced.transactions.distributed.db2.model.Book2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MultipleDatabaseTest {

    @Autowired
    BookDao bookDao1;

    @Autowired
    Book2Dao bookDao2;

    @Test
    @Transactional("db1TransactionManager")
    @Rollback(false)
    public void testDb1() {
        Book book = new Book("Java");
        bookDao1.save(book);
        List<Book> books = bookDao1.findAll();
        books.forEach(b -> System.out.println(b.getTitle()));
    }

    @Test
    @Transactional("db1TransactionManager")
    public void testDb1Read() {
        List<Book> books = bookDao1.findAll();
        System.out.println("*******************************");
        books.forEach(b -> System.out.println(b.getTitle()));
        System.out.println("*******************************");
    }

    @Test
    @Transactional("db2TransactionManager")
    @Rollback(false)
    public void testDb2() {
        Book2 book = new Book2("Java");
        bookDao2.save(book);
        List<Book2> books = bookDao2.findAll();
        books.forEach(b -> System.out.println(b.getTitle()));
    }

    @Test
    @Transactional("db2TransactionManager")
    public void testDb2Read() {
        List<Book2> books = bookDao2.findAll();
        System.out.println("*******************************");
        books.forEach(b -> System.out.println(b.getTitle()));
        System.out.println("*******************************");
    }

    @Test
    @Transactional("chainedTransactionManager")
    @Rollback(false)
    public void distributedTransaction() {
        Book book = new Book("distributed Java 3");
        bookDao1.save(book);
        Book2 book2 = new Book2("distributed Java 3");
        bookDao2.save(book2);
    }
}
