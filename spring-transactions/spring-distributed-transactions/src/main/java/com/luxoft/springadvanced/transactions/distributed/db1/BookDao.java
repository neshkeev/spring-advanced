package com.luxoft.springadvanced.transactions.distributed.db1;

import com.luxoft.springadvanced.transactions.distributed.db1.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends JpaRepository<Book, Integer> {
}
