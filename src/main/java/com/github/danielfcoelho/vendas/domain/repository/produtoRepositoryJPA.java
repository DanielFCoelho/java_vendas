package com.github.danielfcoelho.vendas.domain.repository;

import com.github.danielfcoelho.vendas.domain.entity.produto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface produtoRepositoryJPA extends JpaRepository<produto, Integer>{
    
}