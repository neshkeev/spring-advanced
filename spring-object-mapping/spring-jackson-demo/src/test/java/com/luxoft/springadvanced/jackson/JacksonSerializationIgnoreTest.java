package com.luxoft.springadvanced.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class JacksonSerializationIgnoreTest {

    private static final Logger LOG = LoggerFactory.getLogger(JacksonSerializationIgnoreTest.class);

    @Test
    public void testNonDefaultValues() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String entityAsString = mapper.writeValueAsString(new ClientIncludeNonDefault(null, 0, false));

        assertThat(entityAsString, not(containsString("expenses")));
        assertThat(entityAsString, not(containsString("name")));
        assertThat(entityAsString, not(containsString("vip")));
        LOG.info("{}", entityAsString);
    }

    @Test
    public void testIncludeNonDefault() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClientIncludeNonDefault entityObject = new ClientIncludeNonDefault(null, 0, true);

        String entityAsString = mapper.writeValueAsString(entityObject);

        assertThat(entityAsString, not(containsString("expenses")));
        assertThat(entityAsString, not(containsString("name")));
        assertThat(entityAsString, containsString("vip"));
        LOG.info("{}", entityAsString);
    }

    @Test
    public void testFieldIgnoredByNameWithJsonIgnoreProperties() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClientIgnoreExpensesFieldByName entityObject = new ClientIgnoreExpensesFieldByName("John", 0, true);

        String entityAsString = mapper.writeValueAsString(entityObject);

        assertThat(entityAsString, not(containsString("expenses")));
        assertThat(entityAsString, containsString("name"));
        assertThat(entityAsString, containsString("vip"));
        LOG.info("{}", entityAsString);
    }

    @Test
    public void testFieldIgnoredWithJsonIgnore() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClientIgnoreExpensesField entityObject = new ClientIgnoreExpensesField("Joe", 0, false);

        String entityAsString = mapper.writeValueAsString(entityObject);

        assertThat(entityAsString, not(containsString("expenses")));
        assertThat(entityAsString, containsString("name"));
        assertThat(entityAsString, containsString("vip"));
        LOG.info("{}", entityAsString);
    }

    @Test
    public void testIgnoreNullFields() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        Client entityObject = new Client(null, 0, false);

        String entityAsString = mapper.writeValueAsString(entityObject);

        assertThat(entityAsString, containsString("expenses"));
        assertThat(entityAsString, containsString("vip"));
        assertThat(entityAsString, not(containsString("name")));
        LOG.info("{}", entityAsString);
    }
}
