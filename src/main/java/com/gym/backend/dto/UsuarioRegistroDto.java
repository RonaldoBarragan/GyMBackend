package com.gym.backend.dto;

import java.util.List;

import com.gym.backend.models.Documento;
import com.gym.backend.models.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioRegistroDto {
     @NotBlank(message = "El nombre es obligatorio dese backend")
    private String nombre;

    private Documento documento;
    private String email;
    private String usuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<Rol> roles;
}
