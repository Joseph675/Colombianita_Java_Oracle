package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.AliadoInnovaUcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AliadoInnovaUccRepository extends JpaRepository<AliadoInnovaUcc, Long> {

    // Traer SOLO los aliados activos para mostrarlos en la página principal
    List<AliadoInnovaUcc> findByActivo(Integer activo);

    // Buscar un aliado por su nombre exacto (útil para validaciones antes de crear uno nuevo)
    Optional<AliadoInnovaUcc> findByNombre(String nombre);
}