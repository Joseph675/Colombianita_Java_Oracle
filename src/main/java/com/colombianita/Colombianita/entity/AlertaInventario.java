package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// PATRÓN: State — la alerta tiene dos estados (NO_LEIDA → LEIDA).
//   El estado cambia cuando un usuario marca la alerta como revisada desde AlertaInventarioController.
// PATRÓN: Observer (JPA Lifecycle) — @PrePersist asigna la fecha de creación automáticamente.
@Entity
@Table(name = "alerta_inventario")
public class AlertaInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long idAlerta;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    @Column(name = "fecha_alerta")
    private LocalDateTime fechaAlerta;

    @Column(name = "mensaje", nullable = false, length = 255)
    private String mensaje;

    // PATRÓN: State — campo que representa el estado actual de la notificación
    @Column(name = "estado", length = 20)
    private String estado = "NO_LEIDA";

    public AlertaInventario() {}

    // PATRÓN: Observer (JPA Lifecycle) — listener del evento @PrePersist.
    // Asigna la fecha de la alerta automáticamente en el momento de la inserción.
    @PrePersist
    protected void onCreate() {
        if (this.fechaAlerta == null) {
            this.fechaAlerta = LocalDateTime.now();
        }
    }

    // Getters y Setters
    public Long getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Long idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public LocalDateTime getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(LocalDateTime fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}