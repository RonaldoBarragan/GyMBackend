package com.gym.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gym.backend.models.Cliente;
public interface ClienteRepository extends MongoRepository<Cliente, String> {

   Optional<Cliente> findByDocumentoNumero(String numero);
}