package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

// PATRÓN: Interface Projection (variante del Proxy) — Spring Data JPA implementa esta interfaz
//   en tiempo de ejecución como un proxy dinámico. El repositorio mapea directamente las columnas
//   del @Query a los métodos getX() sin necesidad de una clase concreta.
// PATRÓN: DTO — expone solo los campos necesarios del reporte, sin exponer la entidad Pedido o Cliente completa.
public interface TopClienteDTO {
    Long getIdCliente();
    String getNombres();
    String getCelular();
    BigDecimal getTotalGastado();
}