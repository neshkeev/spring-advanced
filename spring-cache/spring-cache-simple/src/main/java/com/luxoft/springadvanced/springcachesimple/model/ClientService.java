package com.luxoft.springadvanced.springcachesimple.model;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = { "addresses" })
public class ClientService {

    @Cacheable(value = "addresses", key = "#client.name")
    public String getCacheableAddress(Client client) {
        return client.getAddress();
    }

    @Cacheable({ "addresses", "directory" })
    public String getCacheableAddressMultipleCaches(Client client) {
        return client.getAddress();
    }

    @CacheEvict(value = "addresses", allEntries = true)
    public void getAddressCacheEvict(Client ignore) {
    }

    @Caching(evict = {
            @CacheEvict(value="addresses", key = "#client.name"),
            @CacheEvict("directory")
    })
    public void getAddressCacheEvictSelectively(@SuppressWarnings("unused") Client client) {
    }

    @Cacheable
    public String getAddressCacheableNoParameters(Client client) {
        return client.getAddress();
    }

    @CachePut(value = "addresses", key="#client.name", condition = "#client.name=='John'")
    public String getAddressCachePutCondition(Client client) {
        return client.getAddress();
    }

}
