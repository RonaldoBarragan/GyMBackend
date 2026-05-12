package com.gym.backend.models;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "suscripciones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {

    @Id
    private String id;

    private String clienteId;

    private String tipoPlan;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Boolean activa;
}
