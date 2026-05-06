package com.colombianita.Colombianita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vw_ventas_dia")
@Immutable
public class ViewVentasDia {

    @Id
    private Long idCierre;
    private Long idSucursal;
    private LocalDate fecha;
    private Integer totalFacturas;
    private BigDecimal ventaTotal;
    private BigDecimal ticketPromedio;
    private BigDecimal ventaEfectivo;
    private BigDecimal ventaTarjeta;
    private BigDecimal ventaTransferencia;
    private BigDecimal ventaMesa;
    private BigDecimal ventaDomicilio;
    private BigDecimal ventaMostrador;

    // Solo Getters
    public Long getIdCierre() { return idCierre; }
    public Long getIdSucursal() { return idSucursal; }
    public LocalDate getFecha() { return fecha; }
    public Integer getTotalFacturas() { return totalFacturas; }
    public BigDecimal getVentaTotal() { return ventaTotal; }
    public BigDecimal getTicketPromedio() { return ticketPromedio; }
    public BigDecimal getVentaEfectivo() { return ventaEfectivo; }
    public BigDecimal getVentaTarjeta() { return ventaTarjeta; }
    public BigDecimal getVentaTransferencia() { return ventaTransferencia; }
    public BigDecimal getVentaMesa() { return ventaMesa; }
    public BigDecimal getVentaDomicilio() { return ventaDomicilio; }
    public BigDecimal getVentaMostrador() { return ventaMostrador; }
}