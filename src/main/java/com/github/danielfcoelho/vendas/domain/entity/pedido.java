package com.github.danielfcoelho.vendas.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.danielfcoelho.vendas.domain.enums.statusPedido;

@Entity
@Table(name = "pedido")
public class pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")    
    private cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private statusPedido status;

    @OneToMany(mappedBy = "pedido")
    private List<itempedido> itens;

    public Integer getId() {
        return id;
    }

    public statusPedido getStatus() {
        return status;
    }

    public void setStatus(statusPedido status) {
        this.status = status;
    }

    public List<itempedido> getItens() {
        return itens;
    }

    public void setItens(List<itempedido> itens) {
        this.itens = itens;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(final LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(final cliente cliente) {
        this.cliente = cliente;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + this.id + ", total=" + this.total + '\'' + '}';
    }
}