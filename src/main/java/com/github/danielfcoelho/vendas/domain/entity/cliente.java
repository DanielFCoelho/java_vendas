package com.github.danielfcoelho.vendas.domain.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100)
    private String nome;

    @OneToMany(mappedBy = "cliente")
    private Set<pedido> pedidos;

    public cliente() {

    }

    public Set<pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public cliente(String nome) {
        this.nome = nome;
    }

    public cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nome=" + nome + '\'' + '}';
    }
}