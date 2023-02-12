package com.luxoft.springadvanced.projections;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@SuppressWarnings("unused")
@Entity
public class Address {

	@Id
	@GeneratedValue
	private Long id;
	private String zipCode, state, city, street;

	public Address() {
	}

	public Address(String street, String zipCode, String city, String state) {
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.state = state;
	}

	@Override
	public String toString() {
		return "zipCode='" + zipCode + '\'' +
				", state='" + state + '\'' +
				", city='" + city + '\'' +
				", street='" + street + '\'' +
				'}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
