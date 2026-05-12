package com.gym.backend.services;
import com.gym.backend.dto.CrearClienteConSuscripcionDto;
import com.gym.backend.models.Acceso;
import com.gym.backend.models.Cliente;
import com.gym.backend.models.Suscripcion;
import com.gym.backend.repository.AccesoRepository;
import com.gym.backend.repository.ClienteRepository;
import com.gym.backend.repository.SuscripcionRepository;
import java.util.Optional; 
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
    import java.util.Map;

    @Service
    public class ClienteServiceImpl implements ClienteService {
    private final SuscripcionRepository suscripcionRepository;  // ← agregar
    private final AccesoRepository accesoRepository;  
    private final ClienteRepository clienteRepository;

        public ClienteServiceImpl(ClienteRepository clienteRepository,
                               SuscripcionRepository suscripcionRepository,
                               AccesoRepository accesoRepository) {
        this.clienteRepository = clienteRepository;
        this.suscripcionRepository = suscripcionRepository;
        this.accesoRepository = accesoRepository;
    }
        @Override
public Map<String, Object> crearClienteConSuscripcion(CrearClienteConSuscripcionDto dto) {

    // 1. Verificar si ya existe un cliente con ese documento
    Optional<Cliente> existente = clienteRepository
            .findByDocumentoNumero(dto.getDocumento().getNumero());

    if (existente.isPresent()) {
        return Map.of(
            "ok", false,
            "mensaje", "Ya existe un cliente con ese número de documento"
        );
    }

    // 2. Crear el cliente
    Cliente cliente = Cliente.builder()
            .nombre(dto.getNombre())
            .apellido(dto.getApellido())
            .documento(dto.getDocumento())
            .telefono(dto.getTelefono())
            .estado(true)
            .build();

    Cliente clienteGuardado = clienteRepository.save(cliente);

    // 3. Crear la suscripción
    Suscripcion suscripcion = Suscripcion.builder()
            .clienteId(clienteGuardado.getId())
            .fechaInicio(dto.getFechaInicio())
            .fechaFin(dto.getFechaFin())
            .tipoPlan(dto.getTipoPlan())
            .activa(true)
            .build();

    suscripcionRepository.save(suscripcion);

    return Map.of(
        "ok", true,
        "mensaje", "Cliente y suscripción creados correctamente",
        "clienteId", clienteGuardado.getId(),
        "cliente", clienteGuardado.getNombre() + " " + clienteGuardado.getApellido()
    );
}
        @Override
        public List<Cliente> listarClientes() {
            return clienteRepository.findAll();
        }
        @Override
    public Map<String, Object> verificarAcceso(String numeroDocumento) {

        Cliente cliente = clienteRepository
                .findByDocumentoNumero(numeroDocumento)
                .orElse(null);

        if (cliente == null) {
            return Map.of("permitido", false, "mensaje", "Cliente no encontrado");
        }

        if (!cliente.getEstado()) {
            return Map.of("permitido", false, "mensaje", "Cliente inactivo");
        }

        // Verificar suscripción
        Optional<Suscripcion> suscripcionOpt = suscripcionRepository
                .findTopByClienteIdOrderByFechaFinDesc(cliente.getId());

        if (suscripcionOpt.isEmpty()) {
            return Map.of("permitido", false, "mensaje", "Sin suscripción activa");
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaFin = suscripcionOpt.get().getFechaFin();

        if (fechaFin.isBefore(hoy)) {
            return Map.of("permitido", false, "mensaje", "Suscripción vencida");
        }

        // Registrar acceso
        accesoRepository.save(Acceso.builder()
                .clienteId(cliente.getId())
                .fechaEntrada(LocalDateTime.now())
                .build());

        // Avisar si vence pronto
        long dias = ChronoUnit.DAYS.between(hoy, fechaFin);
        if (dias <= 5) {
            return Map.of("permitido", true,
                "mensaje", "Bienvenido " + cliente.getNombre() + 
                           ". Tu suscripción vence en " + dias + " día(s)");
        }

        return Map.of("permitido", true, "mensaje", "Bienvenido " + cliente.getNombre());
    }
    @Override
public Map<String, Object> registrarSalida(String numeroDocumento) {

    // 1. Buscar cliente por documento
    Cliente cliente = clienteRepository
            .findByDocumentoNumero(numeroDocumento)
            .orElse(null);

    if (cliente == null) {
        return Map.of("ok", false, "mensaje", "Cliente no encontrado");
    }

    // 2. Buscar su acceso abierto (sin salida)
    Acceso acceso = accesoRepository
            .findTopByClienteIdAndFechaSalidaIsNullOrderByFechaEntradaDesc(cliente.getId())
            .orElse(null);

    if (acceso == null) {
        return Map.of("ok", false, "mensaje", "No hay entrada activa para este cliente");
    }

    // 3. Registrar salida
    acceso.setFechaSalida(LocalDateTime.now());
    accesoRepository.save(acceso);

    return Map.of("ok", true, "mensaje", "Hasta luego " + cliente.getNombre());
}

}