package com.example.JAMachines.infrestructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.JAMachines.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    // Gera token JWT com claims
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("api-local")
                    .withSubject(user.getId().toString())
                    .withClaim("name", user.getName())
                    .withClaim("email", user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao criar token JWT.", e);
        }
    }

    // Valida token e retorna subject (ID do usuário) ou null se inválido
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-local")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    // Retorna true se o token for válido
    public boolean isValid(String token) {
        return validateToken(token) != null;
    }

    // Extrai ID do usuário do token
    public String extractId(String token) {
        try {
            return JWT.decode(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair ID do token.", e);
        }
    }

    // Extrai email do token
    public String extractEmail(String token) {
        try {
            return JWT.decode(token).getClaim("email").asString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair email do token.", e);
        }
    }

    // Gera data de expiração (2 horas à frente)
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}