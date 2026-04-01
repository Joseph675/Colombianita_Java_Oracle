package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ConvocatoriaInnovaUcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConvocatoriaInnovaUccRepository extends JpaRepository<ConvocatoriaInnovaUcc, Long> {
    
    // Buscar por estado (Ej: traer todas las 'ABIERTA')
    List<ConvocatoriaInnovaUcc> findByEstado(String estado);

    // Buscar convocatorias patrocinadas por un aliado específico
    List<ConvocatoriaInnovaUcc> findByAliadoId(Long aliadoId);
    
    // Ordenar por fecha de cierre (para mostrar las que vencen pronto)
    List<ConvocatoriaInnovaUcc> findAllByOrderByFechaCierreAsc();
}