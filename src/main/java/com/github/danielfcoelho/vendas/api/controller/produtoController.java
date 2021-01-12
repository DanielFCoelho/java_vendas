package com.github.danielfcoelho.vendas.api.controller;

import java.util.List;

import com.github.danielfcoelho.vendas.domain.entity.produto;
import com.github.danielfcoelho.vendas.domain.repository.produtoRepositoryJPA;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class produtoController {
    private produtoRepositoryJPA produtoRepository;

    public produtoController(produtoRepositoryJPA produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("{id}")
    public produto getProdutoById(@PathVariable Integer id) {
        return produtoRepository.findById(id).map(p -> {
            return p;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    public List<produto> findProdutos(produto produtoFiltro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
        Example<produto> example = Example.of(produtoFiltro, matcher);
        return produtoRepository.findAll(example);
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public produto postProduto(@RequestBody produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void putProduto(@PathVariable Integer id, @RequestBody produto produto) {
        produtoRepository.findById(id).map(p -> {
            produto.setId(p.getId());
            produtoRepository.save(produto);
            return p;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduto(@PathVariable Integer id) {
        produtoRepository.findById(id).map(p -> {
            produtoRepository.delete(p);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}