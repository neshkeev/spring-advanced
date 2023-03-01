package com.luxoft.springadvanced.transactions.propagation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.NestedTransactionNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TransactionPropagationTest {

    @Autowired
    private TransactionPropagationController controller;

    @Test
    public void testRequired() {
        assertThrows(IllegalTransactionStateException.class, controller::required);
    }

    @Test
    public void testRequiresNew() {
        assertThrows(NestedTransactionNotSupportedException.class, controller::requiresNew);
    }

    @Test
    public void testSupports() {
        controller.supports();
    }

    @Test
    public void testMandatory() {
        assertThrows(IllegalTransactionStateException.class, controller::mandatory);
    }

    @Test
    public void testNested() {
        controller.nested();
    }

    @Test
    public void testNever() {
        controller.never();
    }
}