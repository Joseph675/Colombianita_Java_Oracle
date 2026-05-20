package com.colombianita.Colombianita.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// PATRÓN: Singleton — Spring gestiona esta clase como un bean (@Component), garantizando
//   una única instancia. La clave de firma se construye una sola vez y se reutiliza.
// PATRÓN: Strategy (soporte) — AuthService delega la generación/validación del token aquí,
//   permitiendo cambiar el algoritmo o la librería JWT sin tocar la lógica de autenticación.
@Component
public class JwtUtil {

    // IMPORTANTE: En producción esta clave debe estar en tu application.properties
    private static final String SECRET_KEY_STRING = "ColombianitaSecretaSuperSeguraYMuyLarga123456"; 
    private static final long EXPIRATION_TIME = 86400000; // 1 día en milisegundos

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}