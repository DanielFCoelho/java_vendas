package com.github.danielfcoelho.vendas.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class informacoesPedidoDTO {
    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private String dataPedido;
    private BigDecimal total;
    private String status;
    private List<InformacoesItemPedidoDTO> itens;
}