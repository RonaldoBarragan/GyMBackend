package com.gym.backend.repository;

import com.gym.backend.models.Suscripcion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SuscripcionRepository extends MongoRepository<Suscripcion, String> {

    Optional<Suscripcion> findTopByClienteIdOrderByFechaFinDesc(String clienteId);
        // Con el campo activa
    Optional<Suscripcion> findTopByClienteIdAndActivaIsTrueOrderByFechaFinDesc(String clienteId);

}