package com.luxoft.springadvanced.transactions.repositories;

import java.time.LocalDate;
import java.util.List;

import com.luxoft.springadvanced.transactions.domain.Book;
import com.luxoft.springadvanced.transactions.domain.DuplicateBookTitleException;
import com.luxoft.springadvanced.transactions.domain.Log;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BookDaoImpl implements BookDaoCustom {

	private final BookDao bookDao;
	private final LogDao log;

	public BookDaoImpl(@Lazy BookDao bookDao, @Lazy LogDao log) {
		this.bookDao = bookDao;
		this.log = log;
	}

	@Transactional
	@Override
	public void setPublisher(String publisher) {
		List<Book> all = bookDao.findAll();
		for (Book b: all) {
			b.setPublisher(publisher);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public void checkTitleDuplicate(String title) {
		final var exists = bookDao.existsByTitle(title);
		if (exists) {
			throw new DuplicateBookTitleException("Book with title '" + title + "' already exists");
		}
	}
	
	@Override
	@Transactional
	public void addBook(String title, LocalDate dateRelease) {
		log.log("adding book with name " + title);
		checkTitleDuplicate(title);
		bookDao.save(new Book(title, dateRelease));
	}

	@Override
	@Transactional(noRollbackFor=DuplicateBookTitleException.class)
	public void addBookNoRollback(String title, LocalDate dateRelease) {
		log.save(new Log("adding log in method with no rollback for book "+title));
		checkTitleDuplicate(title);
		bookDao.save(new Book(title, dateRelease));
	}

	@Override
	@Transactional
	public void addLogs() {
		log.addSeparateLogsNotSupported();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void showLogs() {
		log.showLogs();
	}
	
}
