package com.luxoft.springadvanced.springtesting.annotations.context;

import com.luxoft.springadvanced.springtesting.annotations.Country;
import com.luxoft.springadvanced.springtesting.annotations.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Bean("janeDoe")
	public static Person janeDoe() {
		Country country = new Country("USA", "US");
		Person person = new Person("Jane Doe");
		person.setCountry(country);
		return person;
	}

	@Bean("johnSmith")
	public static Person johnSmith() {
		Country country = new Country("USA", "US");
		Person person = new Person("John Smith");
		person.setCountry(country);
		return person;
	}

}
