package com.luxoft.springadvanced.mapstruct.loop.domain;

import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    private Country country;
    private boolean isRegistered;

    public Person() {}
    public Person(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}