package com.forneceplus.Backend.Repositories;

import com.forneceplus.Backend.Entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
