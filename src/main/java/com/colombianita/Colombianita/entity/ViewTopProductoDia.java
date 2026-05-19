package com.colombianita.Colombianita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

// PATRÓN: Read Model (CQRS parcial) — entidad de solo lectura mapeada a la vista vw_top_productos_dia.
//   Contiene campos calculados (vecesPedido, ingresosGenerados, ranking) que Oracle agrega por día.
//   @Immutable garantiza que Hibernate nunca intente hacer INSERT/UPDATE sobre esta vista.
@Entity
@Table(name = "vw_top_productos_dia")
@Immutable
public class ViewTopProductoDia {

    @Id
    private Long idProducto;
    private String nombreProducto;
    private String categoria;
    private String nombrePresentacion;
    private BigDecimal precio;
    private Integer vecesPedido;
    private BigDecimal ingresosGenerados;
    private Integer ranking;

    public Long getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public String getCategoria() { return categoria; }
    public String getNombrePresentacion() { return nombrePresentacion; }
    public BigDecimal getPrecio() { return precio; }
    public Integer getVecesPedido() { return vecesPedido; }
    public BigDecimal getIngresosGenerados() { return ingresosGenerados; }
    public Integer getRanking() { return ranking; }
}