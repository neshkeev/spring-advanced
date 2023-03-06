package com.luxoft.springadvanced.springtesting.annotations.context;

import com.luxoft.springadvanced.springtesting.annotations.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=BeanConfig.class)
public class SpringAppJavaBeansTest {

    @Autowired
    @Qualifier("janeDoe")
    private Person person;

    private Person expectedPerson;

    @BeforeEach
    public void setUp() {
        expectedPerson = BeanConfig.janeDoe();
    }

    @Test
    public void testInitPassenger() {
        assertEquals(expectedPerson, person);
        System.out.println(person);
    }
}
