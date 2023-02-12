package com.luxoft.springadvanced.beanvalidation.error.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SuppressWarnings("unused")
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CountryValidator.class)
public @interface Country {

    String message() default "Country is not allowed and cannot be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
