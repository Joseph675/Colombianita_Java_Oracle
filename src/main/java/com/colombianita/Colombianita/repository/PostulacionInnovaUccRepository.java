package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.PostulacionInnovaUcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulacionInnovaUccRepository extends JpaRepository<PostulacionInnovaUcc, Long> {

    // 1. Para el Administrador/Jurado: Ver todos los proyectos postulados a una convocatoria
    List<PostulacionInnovaUcc> findByConvocatoriaId(Long convocatoriaId);

    // 2. Para el Estudiante: Ver el historial de convocatorias a las que ha aplicado su proyecto
    List<PostulacionInnovaUcc> findByProyectoId(Long proyectoId);

    // 3. Ver postulaciones por estado (Ej: filtrar solo las 'PENDIENTE' para calificar)
    List<PostulacionInnovaUcc> findByConvocatoriaIdAndEstado(Long convocatoriaId, String estado);

    // 4. Validación de seguridad: Comprobar si un proyecto ya se postuló a una convocatoria
    boolean existsByConvocatoriaIdAndProyectoId(Long convocatoriaId, Long proyectoId);
}