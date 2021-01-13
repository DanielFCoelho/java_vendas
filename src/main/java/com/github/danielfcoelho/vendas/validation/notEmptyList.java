package com.github.danielfcoelho.vendas.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.danielfcoelho.vendas.validation.constraintValidation.notEmptyListValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = notEmptyListValidator.class)
public @interface notEmptyList {
    String message() default "A lista não pode ser vazia.";
    
    Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}