package com.forneceplus.Backend.Repositories;

import com.forneceplus.Backend.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
