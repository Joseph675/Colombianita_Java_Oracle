package com.colombianita.Colombianita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;

@Entity
@Table(name = "vw_alertas_inventario")
@Immutable
public class ViewAlertaInventario {

    @Id
    private Long idInventario;
    private Long idSucursal;
    private String ingrediente;
    private String unidadMedida;
    private BigDecimal cantidadActual;
    private BigDecimal cantidadMinima;
    private BigDecimal pctStock;
    private String nivelAlerta;

    public Long getIdInventario() { return idInventario; }
    public Long getIdSucursal() { return idSucursal; }
    public String getIngrediente() { return ingrediente; }
    public String getUnidadMedida() { return unidadMedida; }
    public BigDecimal getCantidadActual() { return cantidadActual; }
    public BigDecimal getCantidadMinima() { return cantidadMinima; }
    public BigDecimal getPctStock() { return pctStock; }
    public String getNivelAlerta() { return nivelAlerta; }
}