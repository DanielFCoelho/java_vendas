package com.github.danielfcoelho.vendas.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.github.danielfcoelho.vendas.api.apiErrors;
import com.github.danielfcoelho.vendas.exception.pedidoNotFoundException;
import com.github.danielfcoelho.vendas.exception.regraNegocioException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public apiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult(); // Carrega os dados de validação e o que foi que falhou.
        List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());

        return new apiErrors(errors);
    }
}