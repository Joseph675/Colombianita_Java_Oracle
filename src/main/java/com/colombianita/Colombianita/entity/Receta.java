package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "receta")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receta")
    private Long idReceta;

    // Relación: La receta es para una Presentación (Ej: Hamburguesa Única)
    @ManyToOne
    @JoinColumn(name = "id_presentacion", nullable = false)
    private PresentacionProducto presentacion;

    // Relación: Qué ingrediente necesita (Ej: Carne de res)
    @ManyToOne
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    // Cuánto gasta de ese ingrediente (Ej: 150 gramos)
    @Column(name = "cantidad_necesaria", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadNecesaria;

    public Receta() {}

    public Long getIdReceta() { return idReceta; }
    public void setIdReceta(Long idReceta) { this.idReceta = idReceta; }

    public PresentacionProducto getPresentacion() { return presentacion; }
    public void setPresentacion(PresentacionProducto presentacion) { this.presentacion = presentacion; }

    public Ingrediente getIngrediente() { return ingrediente; }
    public void setIngrediente(Ingrediente ingrediente) { this.ingrediente = ingrediente; }

    public BigDecimal getCantidadNecesaria() { return cantidadNecesaria; }
    public void setCantidadNecesaria(BigDecimal cantidadNecesaria) { this.cantidadNecesaria = cantidadNecesaria; }
}