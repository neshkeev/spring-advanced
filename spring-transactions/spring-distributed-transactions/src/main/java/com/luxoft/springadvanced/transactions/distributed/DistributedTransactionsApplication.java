package com.luxoft.springadvanced.transactions.distributed;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class DistributedTransactionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributedTransactionsApplication.class, args);
    }

    @SuppressWarnings("deprecation")
    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager transactionManager(
            @Qualifier("db1TransactionManager") PlatformTransactionManager db1,
            @Qualifier("db2TransactionManager") PlatformTransactionManager db2) {
        return new ChainedTransactionManager(db1, db2);
    }
}
