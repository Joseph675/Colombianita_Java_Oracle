package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ProyectoInnovaUcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoInnovaUccRepository extends JpaRepository<ProyectoInnovaUcc, Long> {
    
    // Traer SOLO los proyectos que no han sido borrados (activo = 1)
    List<ProyectoInnovaUcc> findByActivo(Integer activo);
    
    // Traer todos los proyectos de un creador en específico
    List<ProyectoInnovaUcc> findByUsuarioId(Long idUsuario);

    // Traer proyectos por estado (ej: solo los 'DESTACADO')
    List<ProyectoInnovaUcc> findByEstadoAndActivo(String estado, Integer activo);
}