package com.luxoft.springadvanced.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "expenses" })
public record ClientIgnoreExpensesFieldByName(String name, int expenses, boolean vip) {}
