package com.github.danielfcoelho.vendas.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDTO {
    private Integer produto;
    private Integer quantidade;
}
