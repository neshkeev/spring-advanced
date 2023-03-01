package com.luxoft.springadvanced.transactions.distributed.db2;


import com.luxoft.springadvanced.transactions.distributed.db2.model.Book2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2Dao extends JpaRepository<Book2, Integer> {
}
