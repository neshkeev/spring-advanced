package com.luxoft.springadvanced.transactions.dao;

import com.luxoft.springadvanced.transactions.domain.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface BookRepository extends CrudRepository<Book, Integer> {

    @Override
    @Modifying
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    <S extends Book> S save(S entity);
}
