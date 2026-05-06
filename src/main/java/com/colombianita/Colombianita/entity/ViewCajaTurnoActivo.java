package com.colombianita.Colombianita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vw_caja_turno_activo")
@Immutable
public class ViewCajaTurnoActivo {

    @Id
    private Long idCierre;
    private Long idSucursal;
    private String cajero;
    private LocalDateTime fechaApertura;
    private BigDecimal baseInicial;
    private BigDecimal totalEfectivo;
    private BigDecimal totalTarjetas;
    private BigDecimal totalTransferencias;
    private BigDecimal totalGastos;
    private BigDecimal totalNetoTurno;
    private BigDecimal horasTurnoAbierto;

    public Long getIdCierre() { return idCierre; }
    public Long getIdSucursal() { return idSucursal; }
    public String getCajero() { return cajero; }
    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public BigDecimal getBaseInicial() { return baseInicial; }
    public BigDecimal getTotalEfectivo() { return totalEfectivo; }
    public BigDecimal getTotalTarjetas() { return totalTarjetas; }
    public BigDecimal getTotalTransferencias() { return totalTransferencias; }
    public BigDecimal getTotalGastos() { return totalGastos; }
    public BigDecimal getTotalNetoTurno() { return totalNetoTurno; }
    public BigDecimal getHorasTurnoAbierto() { return horasTurnoAbierto; }
}