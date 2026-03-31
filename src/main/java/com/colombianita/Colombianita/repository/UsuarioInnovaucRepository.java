package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.UsuarioInnovaucc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioInnovaucRepository extends JpaRepository<UsuarioInnovaucc, Long> {
    Optional<UsuarioInnovaucc> findByEmail(String email);
}
