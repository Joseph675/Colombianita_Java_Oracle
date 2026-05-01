package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

public class DetallePedidoDTO {
    private Long idPresentacion;
    private BigDecimal fraccion;
    private BigDecimal precioCobrado;
    private String notas;

    // Getters y Setters
    public Long getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Long idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public BigDecimal getFraccion() {
        return fraccion;
    }

    public void setFraccion(BigDecimal fraccion) {
        this.fraccion = fraccion;
    }

    public BigDecimal getPrecioCobrado() {
        return precioCobrado;
    }

    public void setPrecioCobrado(BigDecimal precioCobrado) {
        this.precioCobrado = precioCobrado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}