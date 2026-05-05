package com.colombianita.Colombianita.repository;
import com.colombianita.Colombianita.entity.InventarioSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioSucursalRepository extends JpaRepository<InventarioSucursal, Long> {
    // Alertas de Desabastecimiento (Stock Mínimo)
    @Query("SELECT i FROM InventarioSucursal i WHERE i.cantidadActual <= i.cantidadMinima AND i.sucursal.idSucursal = :idSucursal")
    List<InventarioSucursal> findAlertasDesabastecimiento(@Param("idSucursal") Long idSucursal);
}
