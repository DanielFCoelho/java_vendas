package com.github.danielfcoelho.vendas.api.controller;

import java.util.List;

import com.github.danielfcoelho.vendas.domain.entity.cliente;
import com.github.danielfcoelho.vendas.domain.repository.clienteRepositoryJPA;

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

@RestController
@RequestMapping("/api/clientes")
public class clienteController {

    private final clienteRepositoryJPA clienteRepository;

    public clienteController(final clienteRepositoryJPA clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("{id}")
    public cliente getClienteById(@PathVariable final Integer id) {
        return clienteRepository.findById(id).map(cliente -> {
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public cliente postCliente(@RequestBody final cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    public void deleteCliente(@PathVariable final Integer id) {
        clienteRepository.findById(id).map(cliente -> {
            clienteRepository.delete(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable final Integer id, @RequestBody final cliente cliente) {
        clienteRepository.findById(id).map(clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            clienteRepository.save(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<cliente> find(final cliente filtro) {
        final ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING);

        Example<cliente> example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }
}

// @Controller
// @RequestMapping("/api/clientes")
// public class clienteController {

// private clienteRepositoryJPA clienteRepository;

// public clienteController(clienteRepositoryJPA clienteRepository) {
// this.clienteRepository = clienteRepository;
// }

// @GetMapping("/{id}")
// @ResponseBody
// public ResponseEntity<cliente> getClienteById(@PathVariable Integer id) {
// Optional<cliente> optionalCliente = clienteRepository.findById(id);
// if (optionalCliente.isPresent()) {
// // HttpHeaders headers = new HttpHeaders();
// // headers.put("Authorization", "token");
// // headers.put("RequestId", "guid");

// // ResponseEntity<cliente> response = new
// // ResponseEntity<>(optionalCliente.get(), headers, HttpStatus.OK);

// return ResponseEntity.ok(optionalCliente.get());
// } else {
// return ResponseEntity.notFound().build();
// }
// }

// @PostMapping()
// @ResponseBody
// public ResponseEntity<cliente> postCliente(@RequestBody cliente cliente) {
// return ResponseEntity.ok(clienteRepository.save(cliente));
// }

// @DeleteMapping("/{id}")
// @ResponseBody
// public ResponseEntity deleteCliente(@PathVariable Integer id) {

// Optional<cliente> clienteEncontrado = clienteRepository.findById(id);

// if (clienteEncontrado.isPresent()) {
// clienteRepository.delete(clienteEncontrado.get());
// return ResponseEntity.noContent().build();
// } else {
// return ResponseEntity.notFound().build();
// }

// }

// @PutMapping("/{id}")
// @ResponseBody
// public ResponseEntity updateCliente(@PathVariable Integer id, @RequestBody
// cliente cliente) {
// return clienteRepository.findById(id).map(clienteExistente -> {
// cliente.setId(clienteExistente.getId());
// clienteRepository.save(cliente);
// return ResponseEntity.noContent().build();
// }).orElseGet(() -> ResponseEntity.notFound().build());
// }

// @GetMapping
// @ResponseBody
// public ResponseEntity find(cliente filtro) {
// ExampleMatcher matcher =
// ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);

// Example example = Example.of(filtro, matcher);
// List<cliente> clientes = clienteRepository.findAll(example);
// return ResponseEntity.ok(clientes);
// }
// }