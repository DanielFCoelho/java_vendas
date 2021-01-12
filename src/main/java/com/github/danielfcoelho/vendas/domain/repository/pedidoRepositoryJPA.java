package com.github.danielfcoelho.vendas.domain.repository;

import java.util.Optional;
import java.util.Set;

import com.github.danielfcoelho.vendas.domain.entity.cliente;
import com.github.danielfcoelho.vendas.domain.entity.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface pedidoRepositoryJPA extends JpaRepository<pedido, Integer> {
    Set<pedido> findByCliente(cliente cliente);

    @Query(value = "select p from pedido p left join p.itens where p.id = :id")
    Optional<pedido> findByIdFetchItens(Integer id);
}