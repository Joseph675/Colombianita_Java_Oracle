package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ProyectoImagenInnovaUcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoImagenInnovaUccRepository extends JpaRepository<ProyectoImagenInnovaUcc, Long> {

    // 1. Traer todas las imágenes de un proyecto, ordenadas para el carrusel (1, 2, 3...)
    List<ProyectoImagenInnovaUcc> findByProyectoIdOrderByOrdenAsc(Long proyectoId);

    // 2. Traer SOLO la imagen principal (la portada) de un proyecto específico
    Optional<ProyectoImagenInnovaUcc> findByProyectoIdAndEsPrincipal(Long proyectoId, Integer esPrincipal);
    
    // 3. Método para borrar todas las imágenes de un proyecto si lo necesitas en Java
    void deleteByProyectoId(Long proyectoId);
}