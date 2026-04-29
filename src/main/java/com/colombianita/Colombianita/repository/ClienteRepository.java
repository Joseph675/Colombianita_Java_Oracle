package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Método útil para que el bot de WhatsApp busque si el cliente ya existe
    Optional<Cliente> findByCelular(String celular);
}