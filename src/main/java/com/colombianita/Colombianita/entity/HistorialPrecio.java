package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_precios")
public class HistorialPrecio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long idHistorial;

    @ManyToOne
    @JoinColumn(name = "id_presentacion", nullable = false)
    private PresentacionProducto presentacion;

    @Column(name = "precio_anterior", precision = 10, scale = 2)
    private BigDecimal precioAnterior;

    @Column(name = "precio_nuevo", precision = 10, scale = 2)
    private BigDecimal precioNuevo;

    @Column(name = "fecha_cambio")
    private LocalDateTime fechaCambio;

    @Column(name = "usuario_db", length = 50)
    private String usuarioDb = "APP_USER"; // Por defecto, o sobreescrito por la DB

    public HistorialPrecio() {}

    @PrePersist
    protected void onCreate() {
        if (this.fechaCambio == null) {
            this.fechaCambio = LocalDateTime.now();
        }
    }

    // Getters y Setters
    public Long getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }

    public PresentacionProducto getPresentacion() { return presentacion; }
    public void setPresentacion(PresentacionProducto presentacion) { this.presentacion = presentacion; }

    public BigDecimal getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(BigDecimal precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public BigDecimal getPrecioNuevo() {
        return precioNuevo;
    }

    public void setPrecioNuevo(BigDecimal precioNuevo) {
        this.precioNuevo = precioNuevo;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getUsuarioDb() { return usuarioDb; }
    public void setUsuarioDb(String usuarioDb) { this.usuarioDb = usuarioDb; }
}