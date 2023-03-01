package com.luxoft.springadvanced.transactions.rollbackorcommit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UnexpectedRollbackWhenCommitTest {

    @Test
    public void testSilentRollbackWhenRuntimeException(@Autowired DemoService demoService) {
        assertThrowsExactly(UnexpectedRollbackException.class, demoService::actionWithRuntimeException);
    }

    @Test
    public void testNoSilentRollbackForCheckedException(@Autowired DemoService demoService) {
        assertDoesNotThrow(demoService::actionWithCheckedException);
    }
}
