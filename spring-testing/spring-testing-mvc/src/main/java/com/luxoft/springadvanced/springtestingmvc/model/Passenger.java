package com.luxoft.springadvanced.springtestingmvc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@SuppressWarnings("unused")
@Entity
public class Passenger {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private Country country;
    private boolean isRegistered;

    public Passenger() {
    }

    public Passenger(String name, Country country) {
        this.name = name;
        this.country = country;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return isRegistered == passenger.isRegistered && Objects.equals(id, passenger.id) && Objects.equals(name, passenger.name) && Objects.equals(country, passenger.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, isRegistered);
    }
}
