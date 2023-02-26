package com.luxoft.springadvanced.transactions.repositories;

import java.time.LocalDate;

public interface BookDaoCustom {

	void setPublisher(String publisher);

	void addBook(String title, LocalDate dateRelease);

	void checkTitleDuplicate(String title);

	void addLogs();

	void showLogs();

	void addBookNoRollback(String title, LocalDate dateRelease);

}
