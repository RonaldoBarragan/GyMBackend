package com.gym.backend.models;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "UsuariosAuth")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioAuth {
    @Id
    private String id; // ID único para cada usuario el mismo del perfil
    private String usuario; // dato de usuario para autenticación
    private String password; // dato de contraseña para autenticación encriptada(bcrypt)

    private List<Rol> roles; // lista de roles usando enum

}
