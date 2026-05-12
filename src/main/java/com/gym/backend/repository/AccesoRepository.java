package com.gym.backend.repository;

import com.gym.backend.models.Acceso;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccesoRepository extends MongoRepository<Acceso, String> {

     Optional<Acceso> findTopByClienteIdAndFechaSalidaIsNullOrderByFechaEntradaDesc(String clienteId);
}