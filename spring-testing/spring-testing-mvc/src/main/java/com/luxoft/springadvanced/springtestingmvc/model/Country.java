package com.luxoft.springadvanced.springtestingmvc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@SuppressWarnings("unused")
@Entity
public class Country {

    @Id
    private String codeName;
    private String name;

    public Country() {}

    public Country(String name, String codeName) {
        this.name = name;
        this.codeName = codeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", codeName='" + codeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(codeName, country.codeName) && Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeName, name);
    }
}
