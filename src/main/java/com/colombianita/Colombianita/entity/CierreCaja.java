package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cierre_caja")
public class CierreCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cierre")
    private Long idCierre;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_apertura")
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "base_inicial", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseInicial = BigDecimal.ZERO;

    @Column(name = "total_efectivo", precision = 10, scale = 2)
    private BigDecimal totalEfectivo = BigDecimal.ZERO;

    @Column(name = "total_tarjetas", precision = 10, scale = 2)
    private BigDecimal totalTarjetas = BigDecimal.ZERO;

    @Column(name = "total_transferencias", precision = 10, scale = 2)
    private BigDecimal totalTransferencias = BigDecimal.ZERO;

    @Column(name = "efectivo_declarado", precision = 10, scale = 2)
    private BigDecimal efectivoDeclarado = BigDecimal.ZERO;

    @Column(name = "diferencia", precision = 10, scale = 2)
    private BigDecimal diferencia = BigDecimal.ZERO;

    @Column(name = "estado", length = 20)
    private String estado = "ABIERTA";

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    public CierreCaja() {}

    @PrePersist
    protected void onCreate() {
        if (this.fechaApertura == null) {
            this.fechaApertura = LocalDateTime.now();
        }
    }

    // Getters y Setters
    public Long getIdCierre() { return idCierre; }
    public void setIdCierre(Long idCierre) { this.idCierre = idCierre; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDateTime fechaApertura) { this.fechaApertura = fechaApertura; }

    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }

    public BigDecimal getBaseInicial() { return baseInicial; }
    public void setBaseInicial(BigDecimal baseInicial) { this.baseInicial = baseInicial; }

    public BigDecimal getTotalEfectivo() { return totalEfectivo; }
    public void setTotalEfectivo(BigDecimal totalEfectivo) { this.totalEfectivo = totalEfectivo; }

    public BigDecimal getTotalTarjetas() { return totalTarjetas; }
    public void setTotalTarjetas(BigDecimal totalTarjetas) { this.totalTarjetas = totalTarjetas; }

    public BigDecimal getTotalTransferencias() { return totalTransferencias; }
    public void setTotalTransferencias(BigDecimal totalTransferencias) { this.totalTransferencias = totalTransferencias; }

    public BigDecimal getEfectivoDeclarado() { return efectivoDeclarado; }
    public void setEfectivoDeclarado(BigDecimal efectivoDeclarado) { this.efectivoDeclarado = efectivoDeclarado; }

    public BigDecimal getDiferencia() { return diferencia; }
    public void setDiferencia(BigDecimal diferencia) { this.diferencia = diferencia; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}