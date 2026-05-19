package com.colombianita.Colombianita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// PATRÓN: Read Model (CQRS parcial) — entidad de solo lectura mapeada a la vista vw_estado_mesas.
//   Agrega información de Mesa + Pedido + Cliente en un solo objeto optimizado para la UI.
//   @Immutable impide que Hibernate intente persistir cambios sobre esta clase.
@Entity
@Table(name = "vw_estado_mesas")
@Immutable
public class ViewEstadoMesa {

    @Id
    private Long idMesa;
    private Long idSucursal;
    private Integer numeroMesa;
    private Integer capacidad;
    private String estado;
    private Long idPedido;
    private BigDecimal totalPedido;
    private LocalDateTime horaApertura;
    private Long minutosOcupada;
    private String nombreCliente;
    private String celularCliente;
    private Integer totalItems;

    public Long getIdMesa() { return idMesa; }
    public Long getIdSucursal() { return idSucursal; }
    public Integer getNumeroMesa() { return numeroMesa; }
    public Integer getCapacidad() { return capacidad; }
    public String getEstado() { return estado; }
    public Long getIdPedido() { return idPedido; }
    public BigDecimal getTotalPedido() { return totalPedido; }
    public LocalDateTime getHoraApertura() { return horaApertura; }
    public Long getMinutosOcupada() { return minutosOcupada; }
    public String getNombreCliente() { return nombreCliente; }
    public String getCelularCliente() { return celularCliente; }
    public Integer getTotalItems() { return totalItems; }
}