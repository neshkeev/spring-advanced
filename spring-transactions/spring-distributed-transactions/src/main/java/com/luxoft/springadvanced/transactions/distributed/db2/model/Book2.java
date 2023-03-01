package com.luxoft.springadvanced.transactions.distributed.db2.model;

import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity
public class Book2 {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String title;

    public Book2() {}

    public Book2(String title) {
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
