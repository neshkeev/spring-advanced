package com.luxoft.springadvanced.transactions.controllers;

public class BookWithNoTitleException extends RuntimeException {
    public BookWithNoTitleException() {
        super("Book has no title");
    }
}
