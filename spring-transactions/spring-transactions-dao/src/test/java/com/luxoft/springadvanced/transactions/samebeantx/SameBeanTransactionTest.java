package com.luxoft.springadvanced.transactions.samebeantx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SameBeanTransactionTest {

    @Autowired
    private SameBeanController controller;

    @Test
    public void testDirectCall() {
        assertDoesNotThrow(controller::actionDirect);
    }

    @Test
    public void testProxyCall() {
        assertThrowsExactly(IllegalTransactionStateException.class, controller::actionWithProxy);
    }
}
