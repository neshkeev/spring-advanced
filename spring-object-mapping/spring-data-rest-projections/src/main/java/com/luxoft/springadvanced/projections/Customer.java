package com.luxoft.springadvanced.projections;

import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	private String firstname, lastname;
	private Gender gender;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Address address;

	public Customer() {
	}

	public Customer(String firstname, String lastname, Gender gender, Address address) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	enum Gender {
		MALE, FEMALE
	}
}
