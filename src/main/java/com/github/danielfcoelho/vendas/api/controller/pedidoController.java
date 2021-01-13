package com.github.danielfcoelho.vendas.api.controller;

import com.github.danielfcoelho.vendas.api.dto.InformacoesItemPedidoDTO;
import com.github.danielfcoelho.vendas.api.dto.atualizacaoStatusPedidoDTO;
import com.github.danielfcoelho.vendas.api.dto.informacoesPedidoDTO;
import com.github.danielfcoelho.vendas.api.dto.pedidoDTO;
import com.github.danielfcoelho.vendas.domain.entity.itempedido;
import com.github.danielfcoelho.vendas.domain.entity.pedido;
import com.github.danielfcoelho.vendas.domain.enums.statusPedido;
import com.github.danielfcoelho.vendas.service.pedidoService;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class pedidoController {

    private pedidoService pedidoService;

    public pedidoController(pedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer postPedido(@RequestBody pedidoDTO pedido) {
        pedido pedidoSalvo = pedidoService.save(pedido);
        return pedidoSalvo.getId();
    }

    @GetMapping("{id}")
    public informacoesPedidoDTO getById(@PathVariable Integer id) {
        return pedidoService.obterPedidoCompleto(id).map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id,
            @RequestBody atualizacaoStatusPedidoDTO atualizacaoStatusPedido) {
        String novoStatus = atualizacaoStatusPedido.getNovoStatus();
        pedidoService.atualizaStatusPedido(id, statusPedido.valueOf(novoStatus));
    }

    private informacoesPedidoDTO converter(pedido pedido) {
        return informacoesPedidoDTO.builder().codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf()).nomeCliente(pedido.getCliente().getNome()).total(pedido.getTotal())
                .status(pedido.getStatus().name()).itens(converter(pedido.getItens())).build();
    }

    private List<InformacoesItemPedidoDTO> converter(List<itempedido> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }

        return items.stream()
                .map(i -> InformacoesItemPedidoDTO.builder().descricaoProduto(i.getProduto().getDescricao())
                        .precoUnitario(i.getProduto().getPreco()).quantidade(i.getQuantidade()).build())
                .collect(Collectors.toList());

    }
}