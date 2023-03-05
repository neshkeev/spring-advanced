package com.luxoft.springadvanced.springtesting.annotations;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@SuppressWarnings("unused")
@Entity
public class Person {
    @Id
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Country country;

    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(country, person.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}