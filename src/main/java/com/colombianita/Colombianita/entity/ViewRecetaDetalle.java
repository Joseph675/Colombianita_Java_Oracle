package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "v_receta_detalle")
public class ViewRecetaDetalle {

    @Id
    @Column(name = "id_receta")
    private Long idReceta;

    @Column(name = "id_presentacion")
    private Long idPresentacion;

    @Column(name = "presentacion")
    private String presentacion;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "producto")
    private String producto;

    @Column(name = "id_ingrediente")
    private Long idIngrediente;

    @Column(name = "ingrediente")
    private String ingrediente;

    @Column(name = "costo_unitario")
    private Integer costoUnitario;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "cantidad_necesaria")
    private BigDecimal cantidadNecesaria;

    @Column(name = "costo_ingrediente")
    private BigDecimal costoIngrediente;

    public Long getIdReceta() { return idReceta; }
    public Long getIdPresentacion() { return idPresentacion; }
    public String getPresentacion() { return presentacion; }
    public BigDecimal getPrecio() { return precio; }
    public Long getIdProducto() { return idProducto; }
    public String getProducto() { return producto; }
    public Long getIdIngrediente() { return idIngrediente; }
    public String getIngrediente() { return ingrediente; }
    public Integer getCostoUnitario() { return costoUnitario; }
    public String getUnidadMedida() { return unidadMedida; }
    public BigDecimal getCantidadNecesaria() { return cantidadNecesaria; }
    public BigDecimal getCostoIngrediente() { return costoIngrediente; }
}
