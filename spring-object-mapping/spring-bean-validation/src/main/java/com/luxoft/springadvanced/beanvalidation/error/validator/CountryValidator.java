package com.luxoft.springadvanced.beanvalidation.error.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CountryValidator implements ConstraintValidator<Country, String> {

    public enum FourAsianTigers {
        SOUTH_KOREA("South Korea"),
        SINGAPORE("Singapore"),
        HONG_KONG("Hong Kong"),
        TAIWAN("Taiwan");

        private final String name;

        FourAsianTigers(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final Set<String> FOUR_ASIAN_TIGERS = Arrays.stream(FourAsianTigers.values())
            .map(FourAsianTigers::getName)
            .collect(Collectors.toSet());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) return false;

        return FOUR_ASIAN_TIGERS.contains(value);
    }
}
