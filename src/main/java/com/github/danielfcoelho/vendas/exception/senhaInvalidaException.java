package com.github.danielfcoelho.vendas.exception;

public class senhaInvalidaException extends RuntimeException {
    public senhaInvalidaException() {
        super("Senha inválida.");
    }
}
