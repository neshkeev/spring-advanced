package com.luxoft.springadvanced.beanvalidation.error;

import java.util.Set;

public class PersonUnsupportedFieldPatchException extends RuntimeException {

    public PersonUnsupportedFieldPatchException(Set<String> keys) {
        super("Fields [" +  String.join(",", keys) + "] update is not allow.");
    }
}
