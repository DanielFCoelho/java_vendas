package com.github.danielfcoelho.vendas.api.controller;

import com.github.danielfcoelho.vendas.api.apiErrors;
import com.github.danielfcoelho.vendas.exception.pedidoNotFoundException;
import com.github.danielfcoelho.vendas.exception.regraNegocioException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Intercepta exceções
public class applicationControllerAdvice {

    @ExceptionHandler(regraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public apiErrors handleRegraNegocioException(regraNegocioException ex) {
        String errorMessage = ex.getMessage();
        return new apiErrors(errorMessage);
    }

    @ExceptionHandler(pedidoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public apiErrors handlePedidoNotFoundException(pedidoNotFoundException ex) {
        return new apiErrors(ex.getMessage());
    }
}