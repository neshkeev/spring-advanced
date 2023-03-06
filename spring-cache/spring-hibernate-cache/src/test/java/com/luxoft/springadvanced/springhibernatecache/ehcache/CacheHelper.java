package com.luxoft.springadvanced.springhibernatecache.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.time.Duration;

public class CacheHelper {

    private final CacheManager cacheManager;
    private Cache<Integer, Integer> squareNumberCache;

    public CacheHelper() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        final CacheConfigurationBuilder<Integer, Integer> configBuilder = getCacheBuilder(10);

        squareNumberCache = cacheManager.createCache("calculator", configBuilder);
    }

    public Cache<Integer, Integer> getSquareNumberCache() {
        return squareNumberCache;
    }

    public void configureSquareNumberCache(long entries, long seconds) {
        cacheManager.removeCache("calculator");

        final var config = getCacheBuilder(entries)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(seconds)))
                .build();
        squareNumberCache = cacheManager.createCache("calculator", config);
    }

    private static CacheConfigurationBuilder<Integer, Integer> getCacheBuilder(long entries) {
        return CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class,
                Integer.class,
                ResourcePoolsBuilder.heap(entries));
    }

}
