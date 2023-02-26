package com.luxoft.springadvanced.transactions.repositories;

import com.luxoft.springadvanced.transactions.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookDao extends JpaRepository<Book, Integer>, BookDaoCustom {

	@Transactional(readOnly = true)
	boolean existsByTitle(String title);
}
