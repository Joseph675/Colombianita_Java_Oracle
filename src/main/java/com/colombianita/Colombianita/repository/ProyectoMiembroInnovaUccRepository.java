package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ProyectoMiembroInnovaUcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoMiembroInnovaUccRepository extends JpaRepository<ProyectoMiembroInnovaUcc, Long> {

    // 1. Ver todos los miembros de un proyecto específico (Para mostrar en la página del proyecto)
    List<ProyectoMiembroInnovaUcc> findByProyectoId(Long proyectoId);

    // 2. Ver todos los proyectos a los que pertenece un usuario (Para su perfil personal)
    List<ProyectoMiembroInnovaUcc> findByUsuarioId(Long usuarioId);

    // 3. Método de seguridad: Verificar si el usuario ya está en el proyecto antes de guardarlo
    boolean existsByProyectoIdAndUsuarioId(Long proyectoId, Long usuarioId);
}