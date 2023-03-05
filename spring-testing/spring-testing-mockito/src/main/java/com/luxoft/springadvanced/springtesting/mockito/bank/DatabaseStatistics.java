package com.luxoft.springadvanced.springtesting.mockito.bank;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class DatabaseStatistics {

    private final List<Account> queriedData = new ArrayList<>();

    public List<Account> queryAccountsDatabase() {
        return queriedData;
    }

    public double averageBalance(List<Account> accountsList) {
        return accountsList.stream()
                .mapToDouble(Account::getBalance)
                .average()
                .getAsDouble();
    }

    public long minimumBalance(List<Account> accountsList) {
        return accountsList.stream()
                .mapToLong(Account::getBalance)
                .min()
                .getAsLong();
    }

    public long maximumBalance(List<Account> accountsList) {
        return accountsList.stream()
                .mapToLong(Account::getBalance)
                .max()
                .getAsLong();
    }
}
