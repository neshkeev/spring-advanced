package com.luxoft.springadvanced.springcachesimple;

import com.luxoft.springadvanced.springcachesimple.config.CachingConfig;
import com.luxoft.springadvanced.springcachesimple.model.Client;
import com.luxoft.springadvanced.springcachesimple.model.ClientNameHashCode;
import com.luxoft.springadvanced.springcachesimple.model.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CachingConfig.class)
public class SpringCachingTest {

    private static final Logger LOG = LoggerFactory.getLogger(SpringCachingTest.class);
    public static final String INITIAL_ADDRESS = "Russia, St. Petersburg, Nevsky 42";
    public static final String NEW_ADDRESS = "Russia, Moscow, Red Square 7";

    @Autowired
    private ClientService service;

    @Test
    public void testCacheableAddress() {
        final var john = new Client("John", INITIAL_ADDRESS);
        withTimeLogger(() -> service.getCacheableAddress(john));

        john.setAddress(NEW_ADDRESS);
        final var cacheableAddress = withTimeLogger(() -> service.getCacheableAddress(john));
        assertEquals(INITIAL_ADDRESS, cacheableAddress, "The address should be taken from the cache, so left unchanged");
    }

    @Test
    public void testCacheableAddressMultipleCaches() {
        final var john = new Client("John", INITIAL_ADDRESS);
        withTimeLogger(() -> service.getCacheableAddressMultipleCaches(john));

        john.setAddress(NEW_ADDRESS);

        var address = withTimeLogger(() -> service.getCacheableAddressMultipleCaches(john));

        assertEquals(INITIAL_ADDRESS, address, "The address should be taken from the cache, so unchanged");
    }

    @Test
    public void testAddressCacheEvict() {
        final var john = new Client("John", INITIAL_ADDRESS);

        withTimeLogger(() -> service.getCacheableAddress(john));

        john.setAddress(NEW_ADDRESS);
        service.getAddressCacheEvict(john);

        var address = withTimeLogger(() -> service.getCacheableAddress(john));

        assertEquals(NEW_ADDRESS, address, "The address should be updated");
    }

    @Test
    public void testAddressCacheEvictSelectively() {
        final var john = new Client("John", INITIAL_ADDRESS);
        withTimeLogger(() -> service.getCacheableAddress(john));

        john.setAddress(NEW_ADDRESS);
        service.getAddressCacheEvictSelectively(john);

        final var address = withTimeLogger(() -> service.getCacheableAddress(john));
        assertEquals(NEW_ADDRESS, address, "The address should be updated");
    }

    @Test
    public void testAddressCacheableNoParameters() {
        final var john = new Client("John", INITIAL_ADDRESS);
        withTimeLogger(() -> service.getAddressCacheableNoParameters(john));
        john.setAddress(NEW_ADDRESS);

        final var address = withTimeLogger(() -> service.getAddressCacheableNoParameters(john));

        assertEquals(INITIAL_ADDRESS, address, "The address should not be taken from the cache");
    }

    @Test
    public void testAddressCacheableNoParameters1() {
        final var john = new ClientNameHashCode("John", INITIAL_ADDRESS);
        withTimeLogger(() -> service.getAddressCacheableNoParameters(john));
        john.setAddress(NEW_ADDRESS);

        // Cache is used here because client.hashCode() is based on name and has not changed
        // See details of the default key generation at SimpleKeyGenerator.generateKey():
        // if there's 1 parameter, use it's hashCode as a key,
        // if more - create a hash code from all params
        // also it's possible to implement interface KeyGenerator by overriding
        // Object KeyGenerator.generate(Object target, Method method, Object... params)

        // DEMO: change Client#hashCode method so that it includes the address field
        // demonstrate the difference in the behavior

        final var address = withTimeLogger(() -> service.getAddressCacheableNoParameters(john));

        assertEquals(NEW_ADDRESS, address, "The address should be taken from the cache, so unchanged");
    }
    @Test
    public void testAddressCachePutCondition() {
        final var john = new Client("John", INITIAL_ADDRESS);
        withTimeLogger(() -> service.getAddressCachePutCondition(john));
        john.setAddress(NEW_ADDRESS);
        final var address = withTimeLogger(() -> service.getCacheableAddress(john));

        assertEquals(INITIAL_ADDRESS, address, "The address should be updated");

        final var mike = new Client("Mike", INITIAL_ADDRESS);
        withTimeLogger(() -> service.getAddressCachePutCondition(mike));

        mike.setAddress(NEW_ADDRESS);
        final var cacheableAddress = withTimeLogger(() -> service.getCacheableAddress(mike));
        assertEquals(NEW_ADDRESS, cacheableAddress, "The address should be updated");
    }

    private static<T> T withTimeLogger(Supplier<T> exe) {
        final var start = LocalDateTime.now();
        try {
            return exe.get();
        } finally {
            final var end = LocalDateTime.now();
            LOG.info("Execution took {}ns", Duration.between(start, end).toNanos());
        }
    }

}
