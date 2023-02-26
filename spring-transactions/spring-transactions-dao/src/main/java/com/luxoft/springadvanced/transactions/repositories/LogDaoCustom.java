package com.luxoft.springadvanced.transactions.repositories;

public interface LogDaoCustom {

	public void log(String message);

	void manualTransactionAddLog();

	void showLogs();

	void showLogsInTransaction();

	void addSeparateLogsNotSupported();

	void addSeparateLogsSupports();

	void checkSupportsWithTimeout();
}
