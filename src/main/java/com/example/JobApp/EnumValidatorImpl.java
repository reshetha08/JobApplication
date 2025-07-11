package com.example.JobApp;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private EnumValidator annotations;

    @Override
    public void initialize(EnumValidator annotations){
        this.annotations=annotations;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){

        if(value==null) return false;

        Object[] enumValues = annotations.enumClass().getEnumConstants();

        return Arrays.stream(enumValues).anyMatch(e->value.equalsIgnoreCase(e.toString()));
    }
}

