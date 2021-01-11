package com.github.danielfcoelho.vendas.api.controller;

import java.util.Optional;

import com.github.danielfcoelho.vendas.domain.entity.cliente;
import com.github.danielfcoelho.vendas.domain.repository.clienteRepositoryJPA;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/clientes")
public class clienteController {

    private clienteRepositoryJPA clienteRepository;

    public clienteController(clienteRepositoryJPA clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<cliente> getClienteById(@PathVariable Integer id) {
        Optional<cliente> optionalCliente = clienteRepository.findById(id);
        if (optionalCliente.isPresent()) {
            // HttpHeaders headers = new HttpHeaders();
            // headers.put("Authorization", "token");
            // headers.put("RequestId", "guid");

            // ResponseEntity<cliente> response = new
            // ResponseEntity<>(optionalCliente.get(), headers, HttpStatus.OK);

            return ResponseEntity.ok(optionalCliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<cliente> postCliente(@RequestBody cliente cliente) {
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteCliente(@PathVariable Integer id) {

        Optional<cliente> clienteEncontrado = clienteRepository.findById(id);

        if (clienteEncontrado.isPresent()) {
            clienteRepository.delete(clienteEncontrado.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity updateCliente(@PathVariable Integer id, @RequestBody cliente cliente) {
        return clienteRepository.findById(id).map(clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            clienteRepository.save(cliente);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}