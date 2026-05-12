package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

public class CostoRecetaDTO {

    private Long idPresentacion;
    private BigDecimal costoTotal;
    private Long cantidadIngredientes;

    public CostoRecetaDTO(Long idPresentacion, BigDecimal costoTotal, Long cantidadIngredientes) {
        this.idPresentacion = idPresentacion;
        this.costoTotal = costoTotal;
        this.cantidadIngredientes = cantidadIngredientes;
    }

    public Long getIdPresentacion() { return idPresentacion; }
    public BigDecimal getCostoTotal() { return costoTotal; }
    public Long getCantidadIngredientes() { return cantidadIngredientes; }
}
