package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

public class MenuBotDTO {
    
    private Long idPresentacion;
    private String categoria;
    private String producto;
    private String presentacion;
    private BigDecimal precio;

    public MenuBotDTO() {
    }

    public Long getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Long idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}