package com.gym.backend.dto;

import java.util.List;

import com.gym.backend.models.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UsuarioAuthDto {

    private String id; //
    private String usuario;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; // La contraseña solso se escribe, no se lee en las respuestas JSON

    // private List<String> roles; // Lista de roles para autorización (ejemplo:
    // "ROLE_USER", "ROLE_ADMIN")

    private List<Rol> roles; // roles controlados por enum



}
