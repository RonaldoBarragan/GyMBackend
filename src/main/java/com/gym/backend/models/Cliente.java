package com.gym.backend.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    private String id;

    private String nombre;

    private String apellido;

   @NotNull
    private Documento documento; // documento de identidad del usuario objeto con tipo y número

    private String telefono;

    private Boolean estado;
}

