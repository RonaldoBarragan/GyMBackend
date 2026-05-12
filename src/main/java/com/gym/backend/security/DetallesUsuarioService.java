package com.gym.backend.security;



import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.gym.backend.models.UsuarioAuth;
import com.gym.backend.repository.UsuarioAuthRepository;

@Service
public class DetallesUsuarioService implements UserDetailsService {

    // Inyeccion dependencias de UsuarioAuthRepository para consultas con la base de
    // datos
    private final UsuarioAuthRepository uar; // uar es el alias para envocar el repositorio

    public DetallesUsuarioService(UsuarioAuthRepository uar) {
        this.uar = uar;
    }

    @Override
    // librerias:
    // UserDetails -> org.springframework.security.core.userdetails.UserDetails
    // Este método:
    // Busca el usuario en BD
    // Lo adapta a un objeto que Spring entiende
    // Devuelve un UserDetails
    public UserDetails loadUserByUsername(String username) { // Es nombre del metodo loadUserByUsername es obligatorio
                                                             // porque: Es parte del contrato de Spring Security y
                                                             // Spring lo llama internamente

        UsuarioAuth usuario = uar.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado sp"));

        return User.builder()
                .username(usuario.getUsuario()) // Establece el nombre de usuario
                .password(usuario.getPass()) // Establece la contraseña (debería estar codificada)
                .authorities(
                        usuario.getRoles().stream()
                                .map(Enum::name)
                                .toArray(String[]::new))
                // Puedes asignar roles según
                // tu lógica
                .build();
    }

}
