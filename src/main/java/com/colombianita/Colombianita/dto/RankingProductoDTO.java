package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

public interface RankingProductoDTO {
    Long getIdPresentacion();
    String getNombreProducto();
    String getNombrePresentacion();
    BigDecimal getCantidadVendida();
}