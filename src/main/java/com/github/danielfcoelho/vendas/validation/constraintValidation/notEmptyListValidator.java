package com.github.danielfcoelho.vendas.validation.constraintValidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.danielfcoelho.vendas.validation.notEmptyList;



public class notEmptyListValidator implements ConstraintValidator<notEmptyList, List> {

    @Override
    public boolean isValid(List value, final ConstraintValidatorContext context) {
        return value != null && !value.isEmpty();
    }
    
}