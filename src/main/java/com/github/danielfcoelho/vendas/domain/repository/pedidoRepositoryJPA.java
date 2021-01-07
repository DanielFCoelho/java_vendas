package com.github.danielfcoelho.vendas.domain.repository;

import java.util.Set;

import com.github.danielfcoelho.vendas.domain.entity.cliente;
import com.github.danielfcoelho.vendas.domain.entity.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface pedidoRepositoryJPA extends JpaRepository<pedido, Integer> {
    Set<pedido> findByCliente(cliente cliente);
}