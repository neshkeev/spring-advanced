package com.luxoft.springadvanced.transactions.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.time.LocalDate;

@Entity
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String message;
	private Date date;

	public Log() {
	}
	
	public Log(String message) {
		super();
		this.message = message;
		this.date = Date.valueOf(LocalDate.now());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Log [message=" + message + ", date=" + date + "]";
	}

}
