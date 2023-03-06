package com.luxoft.springadvanced.springdatacaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class AutoCacheEvict {
    @Autowired
    private CacheManager cacheManager;

    public void evictAllCaches() {
        for (String cacheName : cacheManager.getCacheNames()) {
            final var cache = cacheManager.getCache(cacheName);
            if (cache == null) continue;

            cache.clear();
        }
    }

    @Scheduled(fixedRate = 1000)
    public void evictAllCachesAtIntervals() {
        System.out.println("evicting cache...");
        evictAllCaches();
    }

}
