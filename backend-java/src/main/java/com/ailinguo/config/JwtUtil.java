package com.ailinguo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    
    // Atributo estático privado para guardar a ÚNICA instância da classe
    private static JwtUtil instancia;
    
    @Value("${app.jwt.secret}")
    private String secret;
    
    @Value("${app.jwt.expiration}")
    private long expiration;
    
    // Construtor PRIVADO: impede a criação de instâncias com 'new JwtUtil()' de fora da classe
    private JwtUtil() {
        System.out.println("🔐 Instância ÚNICA do JwtUtil criada! (Singleton) 🔐");
    }
    
    // Método estático público para obter a instância única
    public static synchronized JwtUtil getInstance() {
        if (instancia == null) {
            instancia = new JwtUtil();
        }
        return instancia;
    }
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateToken(String userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        claims.put("email", email);
        
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
    
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }
    
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

