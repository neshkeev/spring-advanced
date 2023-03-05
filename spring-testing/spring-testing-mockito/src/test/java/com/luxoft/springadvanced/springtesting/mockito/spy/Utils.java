package com.luxoft.springadvanced.springtesting.mockito.spy;

import com.luxoft.springadvanced.springtesting.mockito.bank.Account;

import java.util.Arrays;
import java.util.List;

public class Utils {
    static List<Account> getAccounts() {
        return Arrays.asList(new Account("1", 349),
                new Account("2", 278),
                new Account("3", 319),
                new Account("4", 817),
                new Account("5", 623),
                new Account("6", 978));
    }
}
