package com.gym.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.backend.dto.UsuarioDto;
import com.gym.backend.dto.UsuarioRegistroDto;
import com.gym.backend.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController {

     private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> userById(@PathVariable String id) {
        UsuarioDto usuariop = usuarioService.UsuarioById(id);
        return ResponseEntity.ok(usuariop);
    }

    // crear un nuevo usuario
    @PostMapping()
    public ResponseEntity<UsuarioDto> crear(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto creado = usuarioService.create(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        // return new ResponseEntity<>(creado, HttpStatus.CREATED);
        // return ResponseEntity.ok(usuarioService.create(usuarioDto));
    }

    // listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> todos() {
        return ResponseEntity.ok(usuarioService.ListUsuarios());
    }

    // actualizar un usuario existente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> actualizar(@PathVariable String id, @RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.update(id, usuarioDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/docnum/{docnum}")
    public ResponseEntity<UsuarioDto> userDocNum(@PathVariable String docnum) {
        return ResponseEntity.ok(usuarioService.UsuarioByDocNum(docnum));
    }

    @GetMapping("/docnum2/{docnum}")
    public ResponseEntity<?> userDocNum2(@PathVariable String docnum) {
        try {
            UsuarioDto usuario = usuarioService.UsuarioByDocNum(docnum);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException ex) {
            // Aquí capturas la excepción y devuelves el mensaje al cliente
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/docnum3/{docnum}")
    public ResponseEntity<UsuarioDto> userDocNum3(@PathVariable String docnum) {
        UsuarioDto usuario = usuarioService.UsuarioByDocum(docnum);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioRegistroDto dto) {
        System.out.println("DTO recibido: " + dto);
        System.out.println("Roles: " + dto.getRoles());
        UsuarioRegistroDto creado = usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


}
