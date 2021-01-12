package com.github.danielfcoelho.vendas.api.controller;

import com.github.danielfcoelho.vendas.api.apiErrors;
import com.github.danielfcoelho.vendas.exception.regraNegocioException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Intercepta exceções
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class applicationControllerAdvice {
    @ExceptionHandler(regraNegocioException.class)
    public apiErrors handleRegraNegocioException(regraNegocioException ex) {
        String errorMessage = ex.getMessage();
        return new apiErrors(errorMessage);
    }
}