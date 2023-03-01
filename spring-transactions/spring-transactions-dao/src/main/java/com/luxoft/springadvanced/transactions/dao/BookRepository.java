package com.luxoft.springadvanced.transactions.dao;

import com.luxoft.springadvanced.transactions.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    long countById(int id);
}
