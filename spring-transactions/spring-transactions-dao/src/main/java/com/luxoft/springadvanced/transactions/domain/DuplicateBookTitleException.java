package com.luxoft.springadvanced.transactions.domain;

public class DuplicateBookTitleException extends RuntimeException {
	public DuplicateBookTitleException(String message) {
		super(message);
	}
}
