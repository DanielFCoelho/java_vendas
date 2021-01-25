package com.github.danielfcoelho.vendas.api.controller;

import javax.validation.Valid;

import com.github.danielfcoelho.vendas.api.dto.credenciaisDTO;
import com.github.danielfcoelho.vendas.api.dto.tokenDTO;
import com.github.danielfcoelho.vendas.domain.entity.usuario;
import com.github.danielfcoelho.vendas.exception.senhaInvalidaException;
import com.github.danielfcoelho.vendas.security.jwt.jwtService;
import com.github.danielfcoelho.vendas.service.impl.usuarioServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class usuarioController {
    private final usuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final jwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public usuario postUsuario(@RequestBody @Valid usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public tokenDTO autenticar(@RequestBody credenciaisDTO credenciais) {
        try {
            usuario usu = usuario.builder().login(credenciais.getLogin()).senha(credenciais.getSenha()).build();
            usuarioService.autenticar(usu);

            String token = jwtService.geraToken(usu);

            return new tokenDTO(usu.getLogin(), token);
        } catch (UsernameNotFoundException | senhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}