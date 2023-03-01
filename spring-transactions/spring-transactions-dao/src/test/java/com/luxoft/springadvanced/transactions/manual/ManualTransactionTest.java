package com.luxoft.springadvanced.transactions.manual;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("JUnitMalformedDeclaration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ManualTransactionTest {

    @BeforeEach
    public void beforeEach(@Autowired BookRepository bookRepository) {
        bookRepository.deleteAll();
    }

    @Test
    public void testManualCommit(@Autowired ManualTransactionService service,
                                 @Autowired BookRepository bookRepository) {
        bookRepository.findAll().forEach(System.out::println);
        service.actionCommit();
        System.out.println("after");
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void testManualRollback(@Autowired ManualTransactionService service,
                                   @Autowired BookRepository bookRepository) {
        bookRepository.findAll().forEach(System.out::println);
        service.actionRollback();
        System.out.println("after");
        bookRepository.findAll().forEach(System.out::println);
    }
}
