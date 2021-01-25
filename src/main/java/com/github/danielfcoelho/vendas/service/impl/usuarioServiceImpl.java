package com.github.danielfcoelho.vendas.service.impl;

import javax.transaction.Transactional;

import com.github.danielfcoelho.vendas.domain.entity.usuario;
import com.github.danielfcoelho.vendas.domain.repository.usuarioRepositoryJPA;
import com.github.danielfcoelho.vendas.exception.senhaInvalidaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class usuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private usuarioRepositoryJPA usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // if (!username.equals("cicrano")) {
        // throw new UsernameNotFoundException("Usuário não encontrado.");
        // }
        // return
        // User.builder().username("cicrano").password(encoder.encode("1234")).roles("USER",
        // "ADMIN").build();

        usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        String[] roles = usuario.isAdmin() ? new String[] { "ADMIN", "USER" } : new String[] { "USER" };

        return User.builder().username(usuario.getLogin()).password(usuario.getSenha()).roles(roles).build();

    }

    @Transactional
    public usuario salvar(usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public UserDetails autenticar(usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean isPasswordMatches = encoder.matches(usuario.getSenha(), user.getPassword());

        if(isPasswordMatches){
            return user;
        } else {
            throw new senhaInvalidaException();
        }

    }
}