package com.luxoft.springadvanced.springdatacaching;

import com.luxoft.springadvanced.springdatacaching.model.Book;
import com.luxoft.springadvanced.springdatacaching.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookRepositoryIntegrationTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Book(1, "Dune"));
        repository.save(new Book(2, "Foundation"));
    }

    @Test
    @DisplayName("Given a book that should be cached, when we find it by title, then the result should be put in cache")
    void testBookToBeCached() {
        Optional<Book> dune = repository.findFirstByTitle("Dune");

        assertEquals(dune, getCachedBook("Dune"));
    }

    @Test
    @DisplayName("Given a book that should not be cached, when we find it by title, then the result should not be put in cache")
    void testBookNotToBeCached() {
        repository.findFirstByTitle("Foundation");

        assertEquals(empty(), getCachedBook("Foundation"));
    }

    @Test
    @DisplayName("Cache")
    void testBookToBeEvictedFromCache() throws InterruptedException {
        repository.findFirstByTitle("Dune");

        Thread.sleep(1100);

        assertEquals(empty(), getCachedBook("Dune"));
    }

    private Optional<Book> getCachedBook(String title) {
        return ofNullable(cacheManager.getCache("books"))
                .map(c -> c.get(title, Book.class));
    }

}
