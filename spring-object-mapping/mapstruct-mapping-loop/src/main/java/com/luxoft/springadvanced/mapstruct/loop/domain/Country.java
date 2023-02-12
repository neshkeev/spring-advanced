package com.luxoft.springadvanced.mapstruct.loop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@SuppressWarnings("unused")
@Entity
public class Country {

    @Id
    private String codeName;
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "country")
    private List<Person> persons;

    public Country() {}

    public Country(String codeName, String name) {
        this.codeName = codeName;
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
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
}
