package com.colombianita.Colombianita.entity;
import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "inventario_sucursal ")
public class InventarioSucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    // Relación: El inventario pertenece a una Sucursal
    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    // Relación: El inventario es de un Ingrediente específico
    @ManyToOne
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    @Column(name = "cantidad_actual", precision = 10, scale = 2)
    private BigDecimal cantidadActual = BigDecimal.ZERO;

    @Column(name = "cantidad_minima", precision = 10, scale = 2)
    private BigDecimal cantidadMinima = BigDecimal.ZERO;

    public InventarioSucursal() {}

    public Long getIdInventario() { return idInventario; }
    public void setIdInventario(Long idInventario) { this.idInventario = idInventario; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public Ingrediente getIngrediente() { return ingrediente; }
    public void setIngrediente(Ingrediente ingrediente) { this.ingrediente = ingrediente; }

    public BigDecimal getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(BigDecimal cantidadActual) { this.cantidadActual = cantidadActual; }

    public BigDecimal getCantidadMinima() { return cantidadMinima; }
    public void setCantidadMinima(BigDecimal cantidadMinima) { this.cantidadMinima = cantidadMinima; }
}