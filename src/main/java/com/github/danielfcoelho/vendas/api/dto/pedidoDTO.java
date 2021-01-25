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
    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer cliente;
    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;
    @notEmptyList(message = "{campo.itens-pedido.obrigatorio}")
    List<ItemPedidoDTO> items;
}