package com.luxoft.springadvanced.springtesting.mockito.bank;

public class AccountService {

    private final AccountManager accountManager;

    public AccountService(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void transfer(String senderId, String beneficiaryId, long amount) {
        var sender = accountManager.findAccountForUser(senderId);
        var beneficiary = accountManager.findAccountForUser(beneficiaryId);

        sender.withdraw(amount);
        beneficiary.deposit(amount);
    }
}
