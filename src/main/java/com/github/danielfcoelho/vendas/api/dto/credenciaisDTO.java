package com.github.danielfcoelho.vendas.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class credenciaisDTO {
    private String login;
    private String senha;
}