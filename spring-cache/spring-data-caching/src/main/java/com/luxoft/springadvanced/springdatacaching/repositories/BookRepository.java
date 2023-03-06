package com.luxoft.springadvanced.springdatacaching.repositories;

import com.luxoft.springadvanced.springdatacaching.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Cacheable(value = "books", condition = "#a0=='Dune'")
    Optional<Book> findFirstByTitle(String title);
}