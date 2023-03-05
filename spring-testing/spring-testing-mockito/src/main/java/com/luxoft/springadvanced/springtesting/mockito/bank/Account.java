package com.luxoft.springadvanced.springtesting.mockito.bank;

public class Account {
    private final String accountId;
    private long balance;

    public Account(String accountId, long initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public void withdraw(long amount) {
        this.balance -= amount;
    }

    public void deposit(long amount) {
        this.balance += amount;
    }

    public long getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", balance=" + balance +
                '}';
    }
}
