package com.github.danielfcoelho.vendas.domain.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.danielfcoelho.vendas.domain.entity.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class clienteRepositoryEntity {

    @Autowired
    private EntityManager entityManager; // Interface responsável por trabalhar todas as operações das entidades na base
                                         // de dados

    @Transactional // Informa que o método vai gerar uma transação no banco de dados.
    public cliente salvar(cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public cliente atualizar(cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional(readOnly = true)
    public List<cliente> obterClientes() {
        return entityManager.createQuery("from cliente", cliente.class).getResultList();
    }

    @Transactional(readOnly = true)
    public List<cliente> buscarPorNome(final String nome) {
        String jpql = "select c from cliente c where c.nome like :nome";
        TypedQuery<cliente> clienteQuery = entityManager.createQuery(jpql, cliente.class);
        clienteQuery.setParameter("nome", "%" + nome + "%");
        return clienteQuery.getResultList();
    }

    @Transactional
    public void delete(Integer id) {
        cliente cliente = entityManager.find(cliente.class, id);
        delete(cliente);
    }

    @Transactional
    public void delete(cliente cliente) {
        if (!entityManager.contains(cliente)) {
            entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }
}