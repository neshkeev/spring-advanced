package com.luxoft.springadvanced.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public record ClientIncludeNonDefault(String name, int expenses, boolean vip) {}
