package com.luxoft.springadvanced.transactions.repositories;

import com.luxoft.springadvanced.transactions.domain.Log;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class LogDaoImpl implements LogDaoCustom {
	private final LogDao log;
	private final PlatformTransactionManager transactionManager;

	public LogDaoImpl(@Lazy LogDao log, PlatformTransactionManager transactionManager) {
		this.log = log;
		this.transactionManager = transactionManager;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void log(String message) {
		log.save(new Log(message));
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void addSeparateLogsNotSupported() {
		log.save(new Log("check from not supported 1"));
		if (true) throw new RuntimeException();
		log.save(new Log("check from not supported 2"));		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public void addSeparateLogsSupports() {
		log.save(new Log("check from supports 1"));
		if (true) throw new RuntimeException();
		log.save(new Log("check from supports 2"));
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, timeout = 100)
	public void checkSupportsWithTimeout() {
		log.save(new Log("check from supports with timeout 3"));
	}
	
	@Transactional(propagation=Propagation.NEVER)
	public void showLogs() {
		System.out.println("Current log:");
		log.findAll().forEach(System.out::println);
	}

//	@Transactional(propagation=Propagation.NEVER)
	public void manualTransactionAddLog() {
		TransactionStatus status = transactionManager.getTransaction(
				TransactionDefinition.withDefaults());
		try {
			Log log1 = new Log("new manual transaction log");
			log.save(log1);
			transactionManager.commit(status);
		} catch (RuntimeException e) {
			transactionManager.rollback(status);
		}
	}

	@Transactional
	public void showLogsInTransaction() {
		System.out.println("Current log:");
		log.findAll().forEach(System.out::println);
	}
}
