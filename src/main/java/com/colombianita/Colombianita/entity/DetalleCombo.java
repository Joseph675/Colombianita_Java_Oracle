package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_combo")
public class DetalleCombo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_combo")
    private Long idDetalleCombo;

    @ManyToOne
    @JoinColumn(name = "id_combo", nullable = false)
    private Combo combo;

    @ManyToOne
    @JoinColumn(name = "id_presentacion", nullable = false)
    private PresentacionProducto presentacion;

    @Column(nullable = false)
    private Integer cantidad;

    public DetalleCombo() {
    }

    public Long getIdDetalleCombo() {
        return idDetalleCombo;
    }

    public void setIdDetalleCombo(Long idDetalleCombo) {
        this.idDetalleCombo = idDetalleCombo;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    public PresentacionProducto getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(PresentacionProducto presentacion) {
        this.presentacion = presentacion;
    }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}