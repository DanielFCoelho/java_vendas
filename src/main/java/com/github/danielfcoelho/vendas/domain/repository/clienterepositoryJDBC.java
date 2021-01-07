package com.github.danielfcoelho.vendas.domain.repository;

import java.util.List;

import com.github.danielfcoelho.vendas.domain.entity.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class clienterepositoryJDBC {

    private static String INSERT = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL = "select * from cliente";
    private static String UPDATE = "update cliente set nome = ? where id = ?";
    private static String DELETE = "delete from cliente where id = ?";
    private static String SELECT_POR_NOME = SELECT_ALL.concat(" where nome like ?");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public cliente salvar(final cliente cliente) {
        jdbcTemplate.update(INSERT, cliente.getNome());
        return cliente;
    }

    public cliente atualizar(final cliente cliente) {
        jdbcTemplate.update(UPDATE, cliente.getNome(), cliente.getId());
        return cliente;
    }

    public List<cliente> obterClientes() {
        return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
    }

    private RowMapper<cliente> obterClienteMapper() {
        return (RowMapper<cliente>) (resultset, i) -> {
            final Integer id = resultset.getInt("id");
            final String nome = resultset.getString("nome");
            return new cliente(id, nome);
        };
    }

    public List<cliente> buscarPorNome(final String nome) {
        return jdbcTemplate.query(SELECT_POR_NOME, obterClienteMapper(), "%" + nome + "%");
    }

    public void delete(final cliente cliente) {
        delete(cliente.getId());
    }

    public void delete(final Integer id) {
        jdbcTemplate.update(DELETE, new Object[] { id });
    }
}