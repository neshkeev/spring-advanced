package com.luxoft.springadvanced.transactions;

import com.luxoft.springadvanced.transactions.domain.Book;
import com.luxoft.springadvanced.transactions.domain.DuplicateBookTitleException;
import com.luxoft.springadvanced.transactions.repositories.BookDao;
import com.luxoft.springadvanced.transactions.repositories.LogDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDate;


/**
 * Illustrates various transaction propagation attributes.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TransactionPropagationTest {

	private BookDao bookDao;
	private LogDao logDao;
	private static final Logger LOG = LoggerFactory.getLogger(TransactionPropagationTest.class);

	@SuppressWarnings("JUnitMalformedDeclaration")
	@BeforeEach
	public void beforeEach(@Autowired BookDao bookDao, @Autowired LogDao logDao) {
		this.bookDao = bookDao;
		this.logDao = logDao;
	}

	@AfterEach
	public void afterEach() {
		LOG.info("Repository after test:");
		bookDao.findAll().forEach(e -> LOG.info(e.toString()));
		logDao.findAll().forEach(e -> LOG.info(e.toString()));

		bookDao.deleteAll();
		logDao.deleteAll();
	}

	private static void withTransactionStatusLog(Runnable run) {
		LOG.info("Transaction before action {}", TransactionSynchronizationManager.isActualTransactionActive());
		try {
			run.run();
		}
		finally {
			LOG.info("Transaction after action {}", TransactionSynchronizationManager.isActualTransactionActive());
		}
	}

	@Test
	public void testSimple() {
		withTransactionStatusLog(() -> bookDao.save(new Book("Alice in Wonderland", LocalDate.now()))
		);
	}

	@Test
	public void addBook() {
		Book book = new Book("New Book4", null);
		bookDao.save(book);
	}

	@Test
	public void notSupported() {
		// executing in transaction:
		// addLogs is starting transaction, but addSeparateLogsNotSupported() suspends it
		try {
			bookDao.addLogs();
		}
		catch (Exception ignore) {}

		// no transaction - first record is added in the log even after exception
		logDao.showLogs();
	}

	// check if NOT_SUPPORTED is able to see changed outside transaction
	@Test
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void notSupportedSeeChanges() {
		printBooksAmount();
		printBooksAmount();
		suspendTransactionAndPrintBooksAmount();
	}

	// check if NOT_SUPPORTED is able to see changed outside transaction
	@Test
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void notSupportedSeeChanges2() {
		addBook();
		printBooksAmount();
		suspendTransactionAndPrintBooksAmount();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public void suspendTransactionAndPrintBooksAmount() {
		printBooksAmount();
	}

	public void printBooksAmount() {
		System.out.println("********** Books amount: "+ bookDao.findAll().size());
	}

	@Test
	public void supports() {
		// executing without transaction:
		// addSeparateLogsSupports is working with no transaction
		try {
			logDao.addSeparateLogsSupports();
		}
		catch (Exception ignore) {}

		// no transaction - first record is added in the log even after exception
		logDao.showLogs();
	}

	@Test
	@Transactional(timeout = 100) // uncomment to check timeout exception
	public void supportsWithTimeout() {
		// check if @Transactional(propagation=Propagation.SUPPORTS, timeout = 1)
		// will introduce new timeout (set breakpoint inside checkSupportsWithTimout() and check)
		logDao.checkSupportsWithTimeout();

		// no transaction - first record is added in the log even after exception
		logDao.showLogsInTransaction();
	}

	@Test
	public void mandatory() {
		// get exception because checkTitleDuplicate can be executed only in transaction
		try {
			bookDao.checkTitleDuplicate("Java");
		} catch(Exception e) {
			System.out.println("ERROR! "+e.getMessage());
		}
	}

	@Test
	public void never() {
		// it's ok to call showLogs from no transaction
		logDao.showLogs();

		// but prohibited to execute from transaction
		try {
			bookDao.showLogs();
		} catch(Exception e) {
			System.out.println("ERROR! "+ e);
			// IllegalTransactionStateException:
			// Existing transaction found for transaction
			// marked with propagation 'never'
		}
	}

	@Test
	public void requiresNew() {
		// requires new - log message is persisted in the logs even after exception
		// because it was added in the separate transaction
		bookDao.addBook("Java0", LocalDate.of(2015, 5, 1));
		bookDao.addBook("Spring",LocalDate.of(2016, 3, 1));
		bookDao.addBook("Spring Data", LocalDate.of(2016, 1, 1));

		try {
			bookDao.addBook("Spring", LocalDate.of(2016, 3, 1));
		} catch (DuplicateBookTitleException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Logs: ");
		logDao.findAll().forEach(System.out::println);

		System.out.println("List of added books: ");
		bookDao.findAll().forEach(System.out::println);
	}

	@Test
	public void noRollback() {
		// no rollback - log message is persisted in the logs even after exception
		// because transaction was not rolled back
		bookDao.addBookNoRollback("Java", LocalDate.of(2015, 5, 1));
		bookDao.addBookNoRollback("Spring", LocalDate.of(2016, 3, 1));
		bookDao.addBookNoRollback("Spring Data", LocalDate.of(2016, 1, 1));

		try {
			bookDao.addBookNoRollback("Spring", LocalDate.of(2016, 3, 1));
		} catch (DuplicateBookTitleException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Logs: ");
		logDao.findAll().forEach(System.out::println);

		System.out.println("List of added books: ");
		bookDao.findAll().forEach(System.out::println);
	}

}
