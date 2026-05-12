package com.gym.backend.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "UsuariosPerfil")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Usuario {
    @Id
    private String id; 
    @NotBlank
    private String nom; 
   @NotNull
    private Documento doc; // documento de identidad del usuario objeto con tipo y número
    @NotNull
    @Indexed(unique = true)
    private String email; 
    @NotNull
    private String password;

    private Rol rol;

}
