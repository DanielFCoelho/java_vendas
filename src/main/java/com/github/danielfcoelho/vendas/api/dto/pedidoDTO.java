package com.github.danielfcoelho.vendas.api.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class pedidoDTO {
    private Integer cliente;
    private BigDecimal total;
    List<ItemPedidoDTO> items;
}