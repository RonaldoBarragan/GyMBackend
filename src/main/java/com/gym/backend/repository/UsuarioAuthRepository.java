package com.gym.backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gym.backend.models.UsuarioAuth;

public interface UsuarioAuthRepository extends MongoRepository<UsuarioAuth, String> {


    Optional<UsuarioAuth> findByUsuario(String usuario);

}
