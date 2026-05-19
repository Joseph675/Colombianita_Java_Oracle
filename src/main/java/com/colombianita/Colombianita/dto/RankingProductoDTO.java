package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

// PATRÓN: Interface Projection (variante del Proxy) — Spring Data JPA crea un proxy en runtime
//   que implementa esta interfaz y mapea cada columna del @Query al método getX() correspondiente.
// PATRÓN: DTO — projection de solo lectura para el reporte de ranking de ventas por producto.
public interface RankingProductoDTO {
    Long getIdPresentacion();
    String getNombreProducto();
    String getNombrePresentacion();
    BigDecimal getCantidadVendida();
}