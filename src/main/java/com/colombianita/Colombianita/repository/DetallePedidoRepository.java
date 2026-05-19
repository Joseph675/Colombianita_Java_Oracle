package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.dto.RankingProductoDTO;
import com.colombianita.Colombianita.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

// PATRÓN: Repository — abstrae el acceso a datos de la entidad DetallePedido.
// PATRÓN: Template Method — JpaRepository provee las operaciones base; aquí extendemos
//   solo con la consulta de negocio específica (ranking de ventas).
@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    // PATRÓN: Interface Projection — RankingProductoDTO es una interfaz; Spring Data JPA
    //   la implementa como proxy en runtime mapeando cada alias del SELECT al método getX().
    @Query("SELECT pr.idPresentacion as idPresentacion, prod.nombre as nombreProducto, pr.nombrePresentacion as nombrePresentacion, SUM(dp.fraccion) as cantidadVendida " +
           "FROM DetallePedido dp JOIN dp.presentacion pr JOIN pr.producto prod GROUP BY pr.idPresentacion, prod.nombre, pr.nombrePresentacion ORDER BY cantidadVendida DESC")
    List<RankingProductoDTO> findRankingProductos();
}