package com.colombianita.Colombianita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

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