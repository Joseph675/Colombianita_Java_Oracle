package com.colombianita.Colombianita.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonBackReference
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_presentacion", nullable = false)
    private PresentacionProducto presentacion;

    @Column(name = "fraccion", nullable = false, precision = 3, scale = 2)
    private BigDecimal fraccion;

    @Column(name = "precio_cobrado", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCobrado;

    public DetallePedido() {}

    // Getters y Setters
    public Long getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Long idDetalle) { this.idDetalle = idDetalle; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public PresentacionProducto getPresentacion() { return presentacion; }
    public void setPresentacion(PresentacionProducto presentacion) { this.presentacion = presentacion; }

    public BigDecimal getFraccion() { return fraccion; }
    public void setFraccion(BigDecimal fraccion) { this.fraccion = fraccion; }

    public BigDecimal getPrecioCobrado() { return precioCobrado; }
    public void setPrecioCobrado(BigDecimal precioCobrado) { this.precioCobrado = precioCobrado; }
}