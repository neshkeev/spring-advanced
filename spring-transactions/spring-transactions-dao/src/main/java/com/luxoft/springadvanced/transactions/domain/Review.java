package com.luxoft.springadvanced.transactions.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@SuppressWarnings("unused")
@Entity
public class Review {
    @Id
    @GeneratedValue
    private int id;
    private String text;
    @Version
    private int version;

    public Review() {
    }

    public Review(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", version=" + version +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
