package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialPrecioRepository extends JpaRepository<HistorialPrecio, Long> {
    
    // Obtiene todo el historial de cambios de un producto en específico (ordenado por fecha de cambio)
    List<HistorialPrecio> findByPresentacionIdPresentacionOrderByFechaCambioDesc(Long idPresentacion);
}