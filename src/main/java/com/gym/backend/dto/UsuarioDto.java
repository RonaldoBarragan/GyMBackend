package com.gym.backend.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.backend.models.Documento;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

    private String id; // ID único para cada usuario el mismo del autentificación
    private String nombre; // nombre del usuario
    
    private Documento documento; // documento de identidad del usuario objeto con tipo y número
    
    @Email(message = "Formato de email inválido")
    private String email; // correo electrónico del usuario
  

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
