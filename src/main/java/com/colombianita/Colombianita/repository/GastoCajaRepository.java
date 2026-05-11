package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.GastoCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoCajaRepository extends JpaRepository<GastoCaja, Long> {
    
    // Método útil para obtener todos los gastos de un turno/cierre de caja específico
    List<GastoCaja> findByCierreCajaIdCierre(Long idCierre);
}