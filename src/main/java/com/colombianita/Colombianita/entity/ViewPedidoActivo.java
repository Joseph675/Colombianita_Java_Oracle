package com.colombianita.Colombianita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vw_pedidos_activos")
@Immutable
public class ViewPedidoActivo {

    @Id
    private Long idPedido;
    private Long idSucursal;
    private String tipoPedido;
    private String estado;
    private LocalDateTime fechaHora;
    private Long minutosTranscurridos;
    private BigDecimal total;
    private String direccionEntrega;
    private Integer numeroMesa;
    private String nombreCliente;
    private String celularCliente;
    private Integer cantidadItems;
    private String estadoDisplay;

    public Long getIdPedido() { return idPedido; }
    public Long getIdSucursal() { return idSucursal; }
    public String getTipoPedido() { return tipoPedido; }
    public String getEstado() { return estado; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Long getMinutosTranscurridos() { return minutosTranscurridos; }
    public BigDecimal getTotal() { return total; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public Integer getNumeroMesa() { return numeroMesa; }
    public String getNombreCliente() { return nombreCliente; }
    public String getCelularCliente() { return celularCliente; }
    public Integer getCantidadItems() { return cantidadItems; }
    public String getEstadoDisplay() { return estadoDisplay; }
}