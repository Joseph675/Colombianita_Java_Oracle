package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingrediente")
    private Long idIngrediente;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;

      @Column(name = "costo_unitario", nullable = false, length = 20)
    private Integer costoUnitario;

    public Ingrediente() {}

    public Long getIdIngrediente() { return idIngrediente; }
    public void setIdIngrediente(Long idIngrediente) { this.idIngrediente = idIngrediente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public Integer getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(Integer costoUnitario) { this.costoUnitario = costoUnitario; }
}