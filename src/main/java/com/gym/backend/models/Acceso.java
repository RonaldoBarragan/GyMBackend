package com.gym.backend.models;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "accesos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acceso {

    @Id
    private String id;

    private String clienteId;

    private LocalDateTime fechaEntrada;

    private LocalDateTime fechaSalida;
}