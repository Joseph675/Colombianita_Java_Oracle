package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

// PATRÓN: DTO — proyecta los datos del menú en un formato plano y simplificado
//   específicamente diseñado para el consumo del bot de WhatsApp via n8n.
//   Evita exponer relaciones JPA complejas (Producto → Categoria, etc.) al bot.
public class MenuBotDTO {
    
    private Long idPresentacion;
    private String categoria;
    private String producto;
    private String presentacion;
    private String descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}