package com.gym.backend.security;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// esto hace que la clase sea de la configuracion del sistema

// se cargan beans atutomaticamente y configutaciones del CORS.
public class SecurityConfig {

    @Bean
    // el autentication manager automaticamente hace uso e DetallesUsuarioService y
    // PasswordEncoder
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(
                            "/auth/**",
                            "/api/usuarios/registrar",
                            "/clientes/verificar-acceso"
                    ).permitAll()

                    .anyRequest().authenticated()
            )

            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

    return http.build();
}
    private final JwtAuthenticationFilter jwtFilter;

public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
}
    @Bean
    // Define reglas de acceso desde frontend
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));// Solo permite peticiones desde:
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));// Permite estos métodos HTTP
        config.setAllowedHeaders(List.of("*"));// Permite cualquier header
        config.setAllowCredentials(true);
        // Permite: cookies, headers de autenticación

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);// Aplica CORS a TODAS las rutas

        return source;
    }
}

/*
 * Resumen
 * Frontend (React)
 * ↓
 * CORS Config ✔
 * ↓
 * Security FilterChain
 * ↓
 * ¿Ruta pública?
 * ✔ sí → entra
 * no → pide JWT
 */