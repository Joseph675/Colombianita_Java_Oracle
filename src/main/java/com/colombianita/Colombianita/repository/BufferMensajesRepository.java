package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.BufferMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BufferMensajesRepository extends JpaRepository<BufferMensaje, String> {
    // Al ser el CELULAR la llave primaria (String), usaremos el método por defecto findById()
}