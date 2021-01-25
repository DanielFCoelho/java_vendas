package com.github.danielfcoelho.vendas.api;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class apiErrors {
    @Getter
    private List<String> errors;

    public apiErrors(String messageError) {
        this.errors = Arrays.asList(messageError);
    }

    public apiErrors(List<String> messageErrors) {
        this.errors = messageErrors;
    }
}