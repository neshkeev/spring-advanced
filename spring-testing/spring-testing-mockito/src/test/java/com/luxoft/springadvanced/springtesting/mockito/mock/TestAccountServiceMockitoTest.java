package com.luxoft.springadvanced.springtesting.mockito.mock;

import com.luxoft.springadvanced.springtesting.mockito.bank.Account;
import com.luxoft.springadvanced.springtesting.mockito.bank.AccountManager;
import com.luxoft.springadvanced.springtesting.mockito.bank.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestAccountServiceMockitoTest {

    private AccountService accountService;

    @Mock
    private AccountManager mockAccountManager;
    private Account senderAccount;
    private Account beneficiaryAccount;

    @BeforeEach
    public void beforeEach() {
        accountService = new AccountService(mockAccountManager);
        senderAccount = new Account("1", 200);
        beneficiaryAccount = new Account("2", 100);

        Mockito.when(mockAccountManager.findAccountForUser("1"))
                .thenReturn(senderAccount);
        Mockito.when(mockAccountManager.findAccountForUser("2"))
                .thenReturn(beneficiaryAccount);
    }

    @Test
    public void testTransferOk() {
        accountService.transfer("1", "2", 50);

        assertAll(
                () -> assertEquals(150, senderAccount.getBalance()),
                () -> assertEquals(150, beneficiaryAccount.getBalance())
        );
    }
}