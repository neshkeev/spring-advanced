package com.luxoft.springadvanced.transactions.isolation;

import com.luxoft.springadvanced.transactions.dao.BookRepository;
import com.luxoft.springadvanced.transactions.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Controller
public class IsolationController {
    private final BookRepository bookRepository;
    @PersistenceContext
    private EntityManager em;

    public IsolationController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createFirstBook() {
//        bookRepository.deleteAll();
        final var book = bookRepository.findById(0)
                .orElse(new Book("Alice in Wonderland", null));
        book.setTitle("Alice in Wonderland");
        bookRepository.save(book);
        em.flush();
    }

    @Async("concurrent")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFirst(CountDownLatch wait, CountDownLatch notify) throws InterruptedException {
        wait.await();
        try {
            bookRepository.findAll().forEach(System.out::println);
            System.out.println(bookRepository.count());
            final var entity = bookRepository.findById(0).orElseThrow();
            entity.setTitle(UUID.randomUUID().toString());
            bookRepository.save(entity);
            em.flush();
        }
        finally {
            notify.countDown();
        }
    }

    @Async("concurrent")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addNewUncommitted(CountDownLatch wait, CountDownLatch notify, CountDownLatch finish) throws InterruptedException {
        wait.await();
        try {
            final var entity = new Book(UUID.randomUUID().toString(), null);
            bookRepository.save(entity);
        }
        finally {
            notify.countDown();
        }
        finish.await();
    }

    @Async("concurrent")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addNewCommitted(CountDownLatch wait, CountDownLatch notify) throws InterruptedException {
        wait.await();
        try {
            bookRepository.save(new Book(UUID.randomUUID().toString(), null));
        } finally {
            notify.countDown();
        }
    }

    @Async("concurrent")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void lostUpdate1(CountDownLatch wait, CountDownLatch signal, String newTitle) throws InterruptedException {
        final Book first;

        try {
            first = bookRepository.findById(0).orElseThrow();
        }
        finally {
            wait.countDown();
        }
        wait.await();

        first.setTitle(newTitle);
        try {
            bookRepository.save(first);
        }
        finally {
            signal.countDown();
        }
    }

    @Async("concurrent")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void lostUpdate2(CountDownLatch wait, CountDownLatch slot, String newTitle) throws InterruptedException {
        final Book first;
        try {
            first = bookRepository.findById(0).orElseThrow();
        }
        finally {
            wait.countDown();
        }

        wait.await();
        slot.await();

        first.setTitle(newTitle);
        bookRepository.save(first);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void getFirst() {
        final var book = bookRepository.findById(0).orElseThrow();
        em.refresh(book);
//        return book;
    }
}