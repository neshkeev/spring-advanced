package com.luxoft.springadvanced.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ClientIgnoreExpensesField(String name, @JsonIgnore int expenses, boolean vip) {}
