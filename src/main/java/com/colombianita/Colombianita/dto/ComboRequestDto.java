package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ComboRequestDto {
    private String nombre;
    private String descripcion;
    private BigDecimal precioFijo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String diasAplica;
    private List<ItemComboDto> items;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getPrecioFijo() {
        return precioFijo;
    }
    public void setPrecioFijo(BigDecimal precioFijo) {
        this.precioFijo = precioFijo;
    }
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getDiasAplica() { return diasAplica; }
    public void setDiasAplica(String diasAplica) { this.diasAplica = diasAplica; }
    
    public List<ItemComboDto> getItems() { return items; }
    public void setItems(List<ItemComboDto> items) { this.items = items; }
}