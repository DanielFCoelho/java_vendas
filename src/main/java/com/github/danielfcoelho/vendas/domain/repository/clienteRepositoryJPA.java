package com.github.danielfcoelho.vendas.domain.repository;

import java.util.List;

import com.github.danielfcoelho.vendas.domain.entity.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface clienteRepositoryJPA extends JpaRepository<cliente, Integer> {

    List<cliente> findByNomeLike(String Nome); // nome exato

    List<cliente> findByNomeContains(String Nome); // parte do nome (qualquer parte)

    @Query(value = " select c from cliente c where c.nome like concat('%', :nome, '%') ")
    List<cliente> encontraPorNome(@Param("nome") String nome);

    @Query(value = " select * from cliente where nome like '%:nome%' ", nativeQuery = true)
    List<cliente> encontraPorNomeSQL(@Param("nome") String nome);

    Boolean existsByNome(String nome);

    @Modifying
    void deleteByNome(String nome);

    @Query(value = "delete from cliente c where c.nome = :nome")
    @Modifying
    void deletePeloNome(String nome);

    @Query(value = "delete from cliente where nome = ':nome'", nativeQuery = true)
    @Modifying
    void deletePeloNomeSQL(String nome);

    @Query(value = " select c from cliente c left join fetch c.pedidos p where c.id = :id ")
    cliente findClienteFetchPedidos(@Param("id") Integer id);

}