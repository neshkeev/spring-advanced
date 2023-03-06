package com.luxoft.springadvanced.springhibernatecache.ehcache;

public class Calculator {
    private CacheHelper cache;

    public int getSquareValueOfNumber(int input) {
        if (cache.getSquareNumberCache().containsKey(input)) {
            return cache.getSquareNumberCache().get(input);
        }

        System.out.println("Calculating square value of " + input + " and caching result.");

        int squaredValue = input * input;
        cache.getSquareNumberCache().put(input, squaredValue);

        return squaredValue;
    }

    public void setCache(CacheHelper cache) {
        this.cache = cache;
    }
}
