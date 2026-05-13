package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "CARRITO_TEMP")
public class CarritoTemp {

    @Id
    @Column(name = "WHATSAPP_ID", length = 50, nullable = false)
    private String whatsappId;

    @Column(name = "ESTADO", length = 20)
    private String estado = "SELECCIONANDO";

    @Column(name = "ID_PRESENTACION")
    private Long idPresentacion;

    @Column(name = "NOMBRE_PRODUCTO", length = 200)
    private String nombreProducto;

    @Column(name = "PRECIO", precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "NOTAS", length = 500)
    private String notas;

    @Lob
    @Column(name = "CARRITO_JSON")
    private String carritoJson;

    @Column(name = "ULTIMA_ACT")
    private Timestamp ultimaAct;

    public CarritoTemp() {}

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.ultimaAct = new Timestamp(System.currentTimeMillis());
    }

    // Getters y Setters
    public String getWhatsappId() {
        return whatsappId;
    }

    public void setWhatsappId(String whatsappId) {
        this.whatsappId = whatsappId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Long idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getCarritoJson() {
        return carritoJson;
    }

    public void setCarritoJson(String carritoJson) {
        this.carritoJson = carritoJson;
    }

    public Timestamp getUltimaAct() {
        return ultimaAct;
    }

    public void setUltimaAct(Timestamp ultimaAct) {
        this.ultimaAct = ultimaAct;
    }
}