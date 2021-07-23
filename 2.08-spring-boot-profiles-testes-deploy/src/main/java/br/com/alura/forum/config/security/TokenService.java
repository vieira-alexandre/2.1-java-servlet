package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.entities.Usuario;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@Profile({"prod", "test"})
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        Instant instant = Instant.now();
        Date agora = Date.from(instant);
        Date expiracao = Date.from(instant.plusMillis(Long.parseLong(expiration)));
        return Jwts.builder()
                .setIssuer("API do Fórum da Alura")
                .setSubject(logado.getId().toString())
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdusuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
