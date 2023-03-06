package com.luxoft.springadvanced.springhibernatecache.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "departments")
@SuppressWarnings("unused")
public class Department {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private boolean executives;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExecutives(boolean executives) {
        this.executives = executives;
    }

    public boolean isExecutives() {
        return executives;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", executives=" + executives +
                '}';
    }
}
