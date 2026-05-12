package com.gym.backend.controller;

import com.gym.backend.dto.AccesoEntradaDto;
import com.gym.backend.dto.CrearClienteConSuscripcionDto;

import com.gym.backend.models.Cliente;

import com.gym.backend.services.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/registrar")
    public Map<String, Object> registrar(@RequestBody CrearClienteConSuscripcionDto dto) {
        return clienteService.crearClienteConSuscripcion(dto);
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarClientes();
    }

    @PostMapping("/verificar-acceso")
    public Map<String, Object> verificarAcceso(@RequestBody AccesoEntradaDto dto) {
        return clienteService.verificarAcceso(dto.getNumeroDocumento());
    }

    @PostMapping("/registrar-salida")
    public Map<String, Object> registrarSalida(@RequestBody AccesoEntradaDto dto) {
        return clienteService.registrarSalida(dto.getNumeroDocumento());
    }
}