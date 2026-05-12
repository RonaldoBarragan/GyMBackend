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
        public ResponseEntity<Map<String, Object>> login(@RequestBody UsuarioAuthDto dto) {

                // Autentificacion (Spring Security Valida usuario y contraseña)
                Authentication auth = authManager
                                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsuario(),
                                                dto.getPassword()));

                // consultar usuario en Dase de datos
                UsuarioAuth usuario = uar.findByUsuario(dto.getUsuario())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado contro"));

                // extraer el nombre del usuario completo desde la colección de perfil usando el
                // id del usuario auth
                Usuario perfil = usuarioRepo.findById(usuario.getId())
                                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
                // 3️⃣ EXTRAER ROLES (Enum → String)
                List<String> roles = usuario.getRoles()
                                .stream()
                                .map(Enum::name)
                                .toList();

                // 4️⃣ GENERAR TOKEN JWT
                String token = jwtService.generarToken(usuario.getUsuario(), roles, perfil.getNom(), perfil.getDoc());

                // 5️⃣ RESPUESTA (estándar tipo enterprise básica)
                Map<String, Object> respuesta = Map.of(
                                "timestamp", LocalDateTime.now(),
                                "status", 200,
                                "id", usuario.getId(),
                                "mensaje", "Login exitoso",
                                "usuario", usuario.getUsuario(),
                                "nombre", perfil.getNom(),
                                "numeroDocumento", perfil.getDoc(),
                                "roles", roles,
                                "token", token);

                return ResponseEntity.ok(respuesta);
        }
}
