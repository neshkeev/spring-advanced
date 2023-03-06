package com.luxoft.springadvanced.springhibernatecache.ehcache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();
    private final CacheHelper cacheHelper = new CacheHelper();

    @BeforeEach
    void setup() {
        calculator.setCache(cacheHelper);
    }

    @Test
    void calculateOnceTest() {
        for (int i = 1; i <= 10; i++) {
            assertFalse(cacheHelper.getSquareNumberCache().containsKey(i));
            System.out.println("Square value of " + i + ": " + calculator.getSquareValueOfNumber(i) + "\n");
        }
    }

    @Test
    void calculateTwiceTest() {
        int cached = 0;
        int notCached = 0;
        for (int i = 1; i <= 20; i++) {
            assertFalse(cacheHelper.getSquareNumberCache().containsKey(i));
            System.out.println("Square value of " + i + ": " + calculator.getSquareValueOfNumber(i) + "\n");
        }

        for (int i=1; i <= 20; i++) {
            if (cacheHelper.getSquareNumberCache().containsKey(i)) {
                System.out.println("KEY " + i + " is in the cache");
                cached++;
            } else {
                notCached++;
            }
        }

        assertEquals(10, cached);
        assertEquals(10, notCached);

    }

    @Test
    void dataExpiryTest() throws InterruptedException {
        cacheHelper.configureSquareNumberCache(10, 1);

        for (int i = 1; i <= 10; i++) {
            assertFalse(cacheHelper.getSquareNumberCache().containsKey(i));
            System.out.println("Square value of " + i + ": " + calculator.getSquareValueOfNumber(i) + "\n");
        }

        for (int i = 1; i <= 10; i++) {
            assertTrue(cacheHelper.getSquareNumberCache().containsKey(i));
        }

        Thread.sleep(1000);

        for (int i = 1; i <= 10; i++) {
            assertFalse(cacheHelper.getSquareNumberCache().containsKey(i));
        }
    }

}
