package com.gym.backend.services;
import java.util.List;

import com.gym.backend.dto.UsuarioDto;
import com.gym.backend.dto.UsuarioRegistroDto;
public interface UsuarioService {
    UsuarioDto create(UsuarioDto usuarioDto);

    List<UsuarioDto> ListUsuarios();

    UsuarioDto update(String id, UsuarioDto usuarioDto);

    void delete(String id);

    UsuarioDto UsuarioByDocNum(String docnum);

    UsuarioDto UsuarioByDocum(String docnum);

    UsuarioRegistroDto registrarUsuario(UsuarioRegistroDto dto);

    UsuarioDto UsuarioById(String id);
}
