package com.gym.backend.dto;
import java.time.LocalDate;

import com.gym.backend.models.Documento;

import lombok.Data;

@Data
public class CrearClienteConSuscripcionDto {
    //data cliente
    private String nombre;
    private String apellido;
    private Documento documento;
    private String telefono;

    //data suscripcion
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipoPlan;
}