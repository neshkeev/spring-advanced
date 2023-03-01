package com.luxoft.springadvanced.transactions.manual;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import com.luxoft.springadvanced.transactions.domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

@Service
public class ManualTransactionService {

    private final BookRepository bookRepository;
    private final PlatformTransactionManager transactionManager;

    public ManualTransactionService(BookRepository bookRepository, PlatformTransactionManager transactionManager) {
        this.bookRepository = bookRepository;
        this.transactionManager = transactionManager;
    }

    public void actionCommit() {
        var status = transactionManager.getTransaction(TransactionDefinition.withDefaults());
		try {
            final var book = new Book("Alice in Wonderland", null);
            bookRepository.save(book);
			transactionManager.commit(status);
		}
        catch (RuntimeException e) {
			transactionManager.rollback(status);
		}
    }

    public void actionRollback() {
        var status = transactionManager.getTransaction(TransactionDefinition.withDefaults());
        try {
            final var book = throwOnBook();
            bookRepository.save(book);
            transactionManager.commit(status);
        }
        catch (RuntimeException e) {
            transactionManager.rollback(status);
        }
    }

    private static Book throwOnBook() {
        throw new IllegalStateException();
    }
}
