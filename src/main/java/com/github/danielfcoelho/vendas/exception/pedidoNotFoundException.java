package com.github.danielfcoelho.vendas.exception;

public class pedidoNotFoundException extends RuntimeException {
    public pedidoNotFoundException() {
        super("Pedido não encontrado.");
    }
}