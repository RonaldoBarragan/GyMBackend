package com.gym.backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.gym.backend.dto.UsuarioAuthDto;
import com.gym.backend.models.Usuario;
import com.gym.backend.models.UsuarioAuth;
import com.gym.backend.repository.UsuarioAuthRepository;
import com.gym.backend.repository.UsuarioRepository;
import com.gym.backend.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private final JwtService jwtService;
        private final AuthenticationManager authManager;
        private final UsuarioAuthRepository uar;
        private final UsuarioRepository usuarioRepo;

        public AuthController(JwtService jwtService, AuthenticationManager authManager, UsuarioAuthRepository uar,
                        UsuarioRepository usuarioRepo) {
                this.jwtService = jwtService;
                this.authManager = authManager;
                this.uar = uar;
                this.usuarioRepo = usuarioRepo;
        }

        @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody UsuarioAuthDto dto) {

    try {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsuario(),
                        dto.getPassword()));

        UsuarioAuth usuario = uar.findByUsuario(dto.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado contro"));

        Usuario perfil = usuarioRepo.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        List<String> roles = usuario.getRoles()
                .stream()
                .map(Enum::name)
                .toList();

        String token = jwtService.generarToken(
                usuario.getUsuario(),
                roles,
                perfil.getNom(),
                perfil.getDoc());

        Map<String, Object> respuesta = Map.of(
                "token", token,
                "nombre", perfil.getNom(),
                "correo", usuario.getUsuario(),
                "roles", roles
        );

        return ResponseEntity.ok(respuesta);

    } catch (Exception e) {

        e.printStackTrace();

        return ResponseEntity.status(500)
                .body(Map.of(
                        "error", e.getMessage()
                ));
    }
}
}
