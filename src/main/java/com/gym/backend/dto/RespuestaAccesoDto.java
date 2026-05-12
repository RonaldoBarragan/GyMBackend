package com.gym.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespuestaAccesoDto {

    private Boolean accesoPermitido;

    private String mensaje;

    private Long diasRestantes;
}