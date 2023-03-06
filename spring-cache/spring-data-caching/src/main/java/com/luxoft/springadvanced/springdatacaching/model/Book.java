package com.luxoft.springadvanced.springdatacaching.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@SuppressWarnings("unused")
@Entity
public class Book {

    @Id
    private int id;
    private String title;

    public Book() {
    }

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
