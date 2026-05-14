package com.gym.backend.services;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.gym.backend.dto.UsuarioDto;
import com.gym.backend.dto.UsuarioRegistroDto;
import com.gym.backend.mapper.UsuarioMapper;
import com.gym.backend.models.Usuario;
import com.gym.backend.models.UsuarioAuth;
import com.gym.backend.repository.UsuarioAuthRepository;
import com.gym.backend.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository userRepo;

    private final UsuarioAuthRepository authRepo;

    private final UsuarioMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository userRepo, UsuarioAuthRepository authRepo, UsuarioMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Crear un nuevo usuario
    @Override
    public UsuarioDto create(UsuarioDto usuarioDto) {
        Usuario usuario = userMapper.toUsuario(usuarioDto);
        return userMapper.toDto(userRepo.save(usuario));
    }

    // Listar todos los usuarios
    @Override
    public List<UsuarioDto> ListUsuarios() {
        return userMapper.toDtoList(userRepo.findAll());
    }

    // Actualizar un usuario existente
    @Override
    public UsuarioDto update(String id, UsuarioDto usuarioDto) {
        Usuario usuario = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        userMapper.updateUsuario(usuarioDto, usuario);
        return userMapper.toDto(userRepo.save(usuario));
    }

    // Eliminar un usuario por su ID
    @Override
    public void delete(String id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        userRepo.deleteById(id);
    }

    // Buscar un usuario por su número de documento
    @Override
    public UsuarioDto UsuarioByDocNum(String docnum) {
        Usuario usuario = userRepo.findByDocNum(docnum)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con número de documento: " + docnum));
        return userMapper.toDto(usuario);
    }

    // Buscar un usuario por su número de documento (método alternativo) para menjo
    // de excepciones
    @Override
    public UsuarioDto UsuarioByDocum(String docnum) {
        return userRepo.findByDocNum(docnum)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con  el documento: " + docnum));
    }

    @Transactional
    @Override
    public UsuarioRegistroDto registrarUsuario(UsuarioRegistroDto dto) {
        // 1. Guardar perfil
        // 🔥 VALIDACIÓN
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
    
        Usuario perfil = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .nom(dto.getNombre())
                .doc(dto.getDocumento())
                .email(dto.getEmail())
                .build();
    
        Usuario perfilGuardado = userRepo.save(perfil);
    
        UsuarioAuth auth = new UsuarioAuth();
        auth.setId(perfilGuardado.getId());
        dto.setUsuario(dto.getEmail());
        auth.setUsuario(dto.getEmail());
        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        auth.setRoles(dto.getRoles());
        
        authRepo.save(auth);
        
        return dto;
        }

    @Override
    public UsuarioDto UsuarioById(String id) {
        Usuario usuario = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + id));
        return userMapper.toDto(usuario);
    }
}
