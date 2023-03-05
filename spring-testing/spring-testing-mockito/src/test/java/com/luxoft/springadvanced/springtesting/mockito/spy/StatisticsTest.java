package com.luxoft.springadvanced.springtesting.mockito.spy;

import com.luxoft.springadvanced.springtesting.mockito.bank.Account;
import com.luxoft.springadvanced.springtesting.mockito.bank.DatabaseStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsTest {

    @Spy
    private DatabaseStatistics databaseStatistics;
    private final List<Account> accountsList = Utils.getAccounts();

    @BeforeEach
    void before() {
        when(databaseStatistics.queryAccountsDatabase())
                .thenReturn(accountsList);
    }

    @Test
    void test() {
        assertAll(
                () -> assertEquals(accountsList.size(), databaseStatistics.queryAccountsDatabase().size()),
                () -> assertEquals(560.666, databaseStatistics.averageBalance(accountsList), 0.001),
                () -> assertEquals(278, databaseStatistics.minimumBalance(accountsList)),
                () -> assertEquals(978, databaseStatistics.maximumBalance(accountsList))
        );
    }
}
