package com.luxoft.springadvanced.springtesting.mockito.mock;

import com.luxoft.springadvanced.springtesting.mockito.bank.Account;
import com.luxoft.springadvanced.springtesting.mockito.bank.AccountManager;
import com.luxoft.springadvanced.springtesting.mockito.bank.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BeanAccountServiceMockitoTest.AccountManagerTestConfiguration.class})
public class BeanAccountServiceMockitoTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountManager mockAccountManager;
    private Account senderAccount;
    private Account beneficiaryAccount;

    @BeforeEach
    public void beforeEach() {
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

        assertEquals(150, senderAccount.getBalance());
        assertEquals(150, beneficiaryAccount.getBalance());
    }

    @TestConfiguration
    static class AccountManagerTestConfiguration {

        @Bean
        public AccountManager accountManager() {
            return Mockito.mock(AccountManager.class);
        }

        @Bean
        public AccountService accountService(AccountManager accountManager) {
            return new AccountService(accountManager);
        }
    }
}
