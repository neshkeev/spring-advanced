package com.luxoft.springadvanced.springdatarest.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@SuppressWarnings("unused")
public class Review {
    @Id
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    private App app;
    @Column(length = 4000)
    private String text;
    private byte score;
    @Version
    private int version;

    public Review() {
    }

    public Review(UUID id, App app, String text, byte score, int version) {
        this.id = id;
        this.app = app;
        this.text = text;
        this.score = score;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return score == review.score && Objects.equals(id, review.id) && Objects.equals(app, review.app) && Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, app, text, score);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte getScore() {
        return score;
    }

    public void setScore(byte score) {
        this.score = score;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", app=" + app +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", version=" + version +
                '}';
    }
}
