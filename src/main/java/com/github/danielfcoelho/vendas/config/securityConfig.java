package com.github.danielfcoelho.vendas.config;

import com.github.danielfcoelho.vendas.security.jwt.jwtAuthFilter;
import com.github.danielfcoelho.vendas.security.jwt.jwtService;
import com.github.danielfcoelho.vendas.service.impl.usuarioServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private usuarioServiceImpl usuarioService;
    @Autowired
    private jwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtAuthFilter() {
        return new jwtAuthFilter(jwtService, usuarioService);
    }

    // Traz os objetos responsáveis por fazer a autenticação dos usuários e
    // adicionar esses usuários dentro do contexto do security
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());

        // auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("fulano")
        // .password(passwordEncoder().encode("1234")).roles("USER");
    }

    // Define as autorizações de cada usuário. Quem tem acesso ao o quê.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // configuração que permite segurança entre uma aplicação Web e o backend.
        // Como se trata de uma aplicação rest, temos que desabilitar para trabalhar no
        // modo stateless.
        http.csrf().disable().authorizeRequests().antMatchers("/api/clientes/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/produtos/**").hasRole("ADMIN").antMatchers("/api/pedidos/**")
                .hasAnyRole("USER", "ADMIN").antMatchers(HttpMethod.POST, "/api/usuarios/**").permitAll().anyRequest()
                .authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "swagger-resources/**",
                "/configuration/security", "swagger-ui.html", "/webjars/**");
    }
}