package com.github.danielfcoelho.vendas.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.danielfcoelho.vendas.service.impl.usuarioServiceImpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class jwtAuthFilter extends OncePerRequestFilter {

    private final jwtService jwtService;
    private final usuarioServiceImpl usuarioService;

    public jwtAuthFilter(jwtService jwtService, usuarioServiceImpl usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String headerAuthorization = request.getHeader("Authorization");

        if (headerAuthorization != null && headerAuthorization.startsWith("Bearer")) {
            String token = headerAuthorization.split(" ")[1];
            boolean isValidToken = jwtService.tokenValido(token);

            if (isValidToken) {
                String loginUsuario = jwtService.obterLoginUsuario(token);
                UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);

                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(request, response);
    }

}