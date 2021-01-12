package com.github.danielfcoelho.vendas.service;

import java.util.Optional;

import com.github.danielfcoelho.vendas.api.dto.pedidoDTO;
import com.github.danielfcoelho.vendas.domain.entity.pedido;

public interface pedidoService {
 
    pedido save(pedidoDTO pedido);
    Optional<pedido> obterPedidoCompleto(Integer id);
}