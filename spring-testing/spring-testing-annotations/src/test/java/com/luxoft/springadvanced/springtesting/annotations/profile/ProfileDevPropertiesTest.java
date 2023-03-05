package com.luxoft.springadvanced.springtesting.annotations.profile;


import com.luxoft.springadvanced.springtesting.annotations.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ContextConfiguration(classes = ProfileConfig.class)
@ActiveProfiles("dev")
public class ProfileDevPropertiesTest {

    @Value("${custom.profile.name}")
    private String name;

    @Test
    public void test(@Autowired Person person) {
        assertThat(person.getName(), is(name));
    }
}
