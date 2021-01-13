package com.github.danielfcoelho.vendas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.danielfcoelho.vendas.api.dto.ItemPedidoDTO;
import com.github.danielfcoelho.vendas.api.dto.pedidoDTO;
import com.github.danielfcoelho.vendas.domain.entity.cliente;
import com.github.danielfcoelho.vendas.domain.entity.itempedido;
import com.github.danielfcoelho.vendas.domain.entity.pedido;
import com.github.danielfcoelho.vendas.domain.enums.statusPedido;
import com.github.danielfcoelho.vendas.domain.repository.clienteRepositoryJPA;
import com.github.danielfcoelho.vendas.domain.repository.itemPedidoRepositoryJPA;
import com.github.danielfcoelho.vendas.domain.repository.pedidoRepositoryJPA;
import com.github.danielfcoelho.vendas.domain.repository.produtoRepositoryJPA;
import com.github.danielfcoelho.vendas.exception.pedidoNotFoundException;
import com.github.danielfcoelho.vendas.exception.regraNegocioException;
import com.github.danielfcoelho.vendas.service.pedidoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class pedidosServiceImpl implements pedidoService {

    private final pedidoRepositoryJPA pedidoRepository;
    private final produtoRepositoryJPA produtoRepository;
    private final clienteRepositoryJPA clienteRepository;
    private final itemPedidoRepositoryJPA itemPedidoRepository;

    @Override
    @Transactional
    public pedido save(final pedidoDTO pedido) {
        cliente cliente = clienteRepository.findById(pedido.getCliente())
                .orElseThrow(() -> new regraNegocioException("Código de cliente inválido"));

        pedido novoPedido = new pedido();
        novoPedido.setTotal(pedido.getTotal());
        novoPedido.setDataPedido(LocalDate.now());
        novoPedido.setCliente(cliente);
        novoPedido.setStatus(statusPedido.REALIZADO);

        List<itempedido> itemsNovoPedido = converteItems(novoPedido, pedido.getItems());
        pedidoRepository.save(novoPedido);
        itemPedidoRepository.saveAll(itemsNovoPedido);
        novoPedido.setItens(itemsNovoPedido);
        return novoPedido;
    }

    private List<itempedido> converteItems(pedido pedido, List<ItemPedidoDTO> itemsDTO) {
        if (itemsDTO.isEmpty()) {
            throw new regraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return itemsDTO.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            produtoRepository.findById(idProduto)
                    .orElseThrow(() -> new regraNegocioException("Código de produto inválido: " + idProduto));

            itempedido itemPedido = new itempedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            return itemPedido;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatusPedido(Integer id, statusPedido status) {
        pedidoRepository.findById(id).map(p -> {
            p.setStatus(status);
            return pedidoRepository.save(p);
        }).orElseThrow(() -> new pedidoNotFoundException());
    }

}