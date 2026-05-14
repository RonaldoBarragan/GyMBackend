package com.gym.backend.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gym.backend.models.Documento;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Esta es la clave para firmar el token
    // Debe tener mínimo 32 caracteres
    @Value("${jwt.secret}")
    private String CLAVE;

    // private final String CLAVE_SECRETA = "clave-secreta-123456789000000000";

    // Libreria Key -> java.security.Key;
    private Key obtenerClave() {
        // Libreria Keys -> io.jsonwebtoken.security.Keys
        // Convierte el string en una clave válida para JWT
        // genera clave compatible con HS256
        System.out.println("La clave secreta codificada es");
        System.out.println(CLAVE);
        return Keys.hmacShaKeyFor(CLAVE.getBytes());
    }

    // Generar un token JWT
    public String generarToken(
        String usuario,
        List<String> roles,
        String nom,
        Documento numero) {

    return Jwts.builder()
            .setSubject(usuario)
            .claim("roles", roles)
            .claim("nombre", nom)
            .claim("numero", numero.getNumero())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000))
            .signWith(obtenerClave(), SignatureAlgorithm.HS256)
            .compact();
}

    // extraer Datos del token tambien llamado claims // es el generico del token
    public Claims obtenerClaims(String token) {
        return Jwts.parserBuilder() // Analiza el token JWT
                .setSigningKey(obtenerClave()) // Establece la clave de firma
                .build() // Construye el analizador
                .parseClaimsJws(token) // Analiza el token y devuelve los claims
                .getBody();// Obtiene el cuerpo del token (claims)
    }

    // Extraer roles del token
    public List<String> extraerRoles(String token) {
        return obtenerClaims(token).get("roles", List.class); // Obtiene los roles del token
    }

    // extraer usuario del token
    public String extraerUsuario(String token) {
        return obtenerClaims(token).getSubject(); // Obtiene el sujeto del token (el usuario)
    }

}