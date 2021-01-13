package com.github.danielfcoelho.vendas.api.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.github.danielfcoelho.vendas.validation.notEmptyList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class pedidoDTO {
    @NotNull(message = "Código do cliente é obrigatório.")
    private Integer cliente;
    @NotNull(message = "Total do pedido é obrigatório.")
    private BigDecimal total;
    @notEmptyList(message = "Pedido não pode ser realizado sem itens.")
    List<ItemPedidoDTO> items;
}