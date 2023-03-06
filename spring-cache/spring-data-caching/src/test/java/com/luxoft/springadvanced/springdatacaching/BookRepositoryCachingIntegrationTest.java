package com.luxoft.springadvanced.springdatacaching;

import com.luxoft.springadvanced.springdatacaching.model.Book;
import com.luxoft.springadvanced.springdatacaching.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.AopTestUtils;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class BookRepositoryCachingIntegrationTest {

    private static final Book DUNE = new Book(1, "Dune");
    private static final Book FOUNDATION = new Book(2, "Foundation");

    private BookRepository mock;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        mock = AopTestUtils.getTargetObject(bookRepository);

        when(mock.findFirstByTitle(eq("Foundation")))
                .thenReturn(of(FOUNDATION));

        when(mock.findFirstByTitle(eq("Dune")))
                .thenReturn(of(DUNE))
                .thenThrow(new RuntimeException("Book should be cached!"));
    }

    @Test
    @DisplayName("Given a cached book, when we look for it by title, then the repository is not accessed")
    void testFindCachedBookByTitle() {
        assertEquals(of(DUNE), bookRepository.findFirstByTitle("Dune"));
        verify(mock).findFirstByTitle("Dune");

        assertEquals(of(DUNE), bookRepository.findFirstByTitle("Dune"));
        assertEquals(of(DUNE), bookRepository.findFirstByTitle("Dune"));

        verifyNoMoreInteractions(mock);
    }

    @Test
    @DisplayName("Given a not cached book, when we look for it by title, then the repository is accessed")
    void testFindNotCachedBookByTitle() {
        assertEquals(of(FOUNDATION), bookRepository.findFirstByTitle("Foundation"));
        assertEquals(of(FOUNDATION), bookRepository.findFirstByTitle("Foundation"));
        assertEquals(of(FOUNDATION), bookRepository.findFirstByTitle("Foundation"));

        verify(mock, times(3)).findFirstByTitle("Foundation");
    }

    @EnableCaching
    @Configuration
    public static class CachingTestConfig {

        @Bean
        public BookRepository bookRepositoryMockImplementation() {
            return mock(BookRepository.class);
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("books");
        }
    }
}
