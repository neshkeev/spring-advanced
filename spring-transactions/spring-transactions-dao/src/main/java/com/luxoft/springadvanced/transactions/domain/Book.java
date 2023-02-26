package com.luxoft.springadvanced.transactions.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Book {

	@Id
	@GeneratedValue
	private int id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "DATE_RELEASE")
	private java.time.LocalDate dateRelease;
	private String publisher;
	@Version
	private int version;

	public Book() {
	}

	public Book(int id, String title, LocalDate dateRelease) {
		this.id = id;
		this.title = title;
		this.dateRelease = dateRelease;
	}

	public Book(String title, LocalDate dateRelease) {
		this.title = title;
		this.dateRelease = dateRelease;
	}

	@Override
	public String toString() {
		return "Book{" +
				"title='" + title + '\'' +
				", dateRelease=" + dateRelease +
				", publisher='" + publisher + '\'' +
				'}';
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

	public LocalDate getDateRelease() {
		return dateRelease;
	}

	public void setDateRelease(LocalDate dateRelease) {
		this.dateRelease = dateRelease;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}