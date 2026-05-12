package com.gym.backend.services;

import com.gym.backend.dto.CrearClienteConSuscripcionDto;
import com.gym.backend.models.Cliente;

import java.util.List;
import java.util.Map;

public interface ClienteService {

    Map<String, Object> crearClienteConSuscripcion(CrearClienteConSuscripcionDto dto);

    List<Cliente> listarClientes();

    Map<String, Object> verificarAcceso(String numeroDocumento);

    Map<String, Object> registrarSalida(String numeroDocumento);
}