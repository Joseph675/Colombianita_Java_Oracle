package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.AlertaInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaInventarioRepository extends JpaRepository<AlertaInventario, Long> {
    
    // Buscar todas las alertas de una sucursal, ordenadas de más reciente a más antigua
    List<AlertaInventario> findBySucursalIdSucursalOrderByFechaAlertaDesc(Long idSucursal);
    
    // Buscar solo las alertas "NO_LEIDA" de una sucursal para el contador de la campanita
    List<AlertaInventario> findBySucursalIdSucursalAndEstadoOrderByFechaAlertaDesc(Long idSucursal, String estado);

    // Buscar todas las alertas globales por estado
    List<AlertaInventario> findByEstadoOrderByFechaAlertaDesc(String estado);
}