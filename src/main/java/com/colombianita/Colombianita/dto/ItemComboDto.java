package com.colombianita.Colombianita.dto;

public class ItemComboDto {
    private Long idPresentacion;
    private Integer cantidad;

    // Constructor vacío necesario para que Spring/Jackson pueda mapear el JSON
    public ItemComboDto() {
    }

    // Constructor con parámetros para instanciarlo rápidamente desde el Controlador
    public ItemComboDto(Long idPresentacion, Integer cantidad) {
        this.idPresentacion = idPresentacion;
        this.cantidad = cantidad;
    }

    public Long getIdPresentacion() {
        return idPresentacion;
    }
    public void setIdPresentacion(Long idPresentacion) {
        this.idPresentacion = idPresentacion;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}