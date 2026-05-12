package com.gym.backend.mapper;
import java.util.List;

import com.gym.backend.dto.UsuarioDto;
import com.gym.backend.models.Usuario;


public interface UsuarioMapper {
    
    Usuario toUsuario(UsuarioDto usuarioDto); // Método para convertir de UsuarioDto a Usuario

    UsuarioDto toDto(Usuario usuario); // Método para convertir de Usuario a UsuarioDto

    List<UsuarioDto> toDtoList(List<Usuario> usuarios); // Método para convertir una lista de Usuario a una lista de
                                                        // UsuarioDto

    void updateUsuario(UsuarioDto usuarioDto, Usuario usuario); // Método para actualizar un Usuario existente con los
                                                                // datos de un UsuarioDto
}
