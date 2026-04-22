package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Clave para el login: buscar si el email existe
    Optional<Usuario> findByEmail(String email);
}