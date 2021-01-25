package com.github.danielfcoelho.vendas.domain.repository;

import java.util.Optional;

import com.github.danielfcoelho.vendas.domain.entity.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface usuarioRepositoryJPA extends JpaRepository<usuario, Integer> {
    Optional<usuario> findByLogin(String login);
}