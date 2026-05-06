package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

public class CierreCajaRequestDTO {
    private BigDecimal efectivoDeclarado;
    private String observaciones;

    // Getters y Setters
    public BigDecimal getEfectivoDeclarado() {
        return efectivoDeclarado;
    }

    public void setEfectivoDeclarado(BigDecimal efectivoDeclarado) {
        this.efectivoDeclarado = efectivoDeclarado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}