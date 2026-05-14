package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ComboMasivoRequestDto {
    
    private String nombreBase; // Ej: "Combo Pizza {sabor} + Gaseosa"
    private String descripcion;
    private BigDecimal precioFijo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String diasAplica;
    
    // Aquí enviarás todas las IDs de las Pizzas Familiares (Las que varían)
    private List<Long> idPresentacionesVariables;
    
    // Aquí enviarás los productos obligatorios, ej. Gaseosa 1Lt (Los que son fijos)
    private List<ItemComboDto> itemsFijos;

    // Getters y Setters
    public String getNombreBase() { return nombreBase; }
    public void setNombreBase(String nombreBase) { this.nombreBase = nombreBase; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecioFijo() { return precioFijo; }
    public void setPrecioFijo(BigDecimal precioFijo) { this.precioFijo = precioFijo; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public String getDiasAplica() { return diasAplica; }
    public void setDiasAplica(String diasAplica) { this.diasAplica = diasAplica; }

    public List<Long> getIdPresentacionesVariables() { return idPresentacionesVariables; }
    public void setIdPresentacionesVariables(List<Long> idPresentacionesVariables) { this.idPresentacionesVariables = idPresentacionesVariables; }

    public List<ItemComboDto> getItemsFijos() { return itemsFijos; }
    public void setItemsFijos(List<ItemComboDto> itemsFijos) { this.itemsFijos = itemsFijos; }
}