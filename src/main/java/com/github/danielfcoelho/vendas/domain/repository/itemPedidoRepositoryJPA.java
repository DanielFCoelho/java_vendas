package com.github.danielfcoelho.vendas.domain.repository;

import com.github.danielfcoelho.vendas.domain.entity.itempedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface itemPedidoRepositoryJPA extends JpaRepository<itempedido, Integer>{
    
}