package com.github.danielfcoelho.vendas.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.github.danielfcoelho.vendas.VendasApplication;
import com.github.danielfcoelho.vendas.domain.entity.usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class jwtService {
    @Value("${security.jwt.expiracao}")
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String geraToken(usuario usuario) {
        long expLong = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expLong);

        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        // HashMap<String, Object> claims = new HashMap<>();
        // claims.put("emailDoUsuario", "user@gmail.com");
        // claims.put("role", "admin");

        // return
        // Jwts.builder().setSubject(usuario.getLogin()).setExpiration(data).setClaims(claims)
        // .signWith(SignatureAlgorithm.HS512, chaveAssinatura).compact();

        return Jwts.builder().setSubject(usuario.getLogin()).setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura).compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token).getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiration = claims.getExpiration();
            LocalDateTime data = dataExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
        jwtService service = context.getBean(jwtService.class);
        usuario user = usuario.builder().login("teste").build();
        String token = service.geraToken(user);
        System.out.println(token);

        boolean isTokenValido = service.tokenValido(token);
        System.out.println("Token válido? " + isTokenValido);

        System.out.println(service.obterLoginUsuario(token));
    }
}