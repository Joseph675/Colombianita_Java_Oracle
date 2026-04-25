package com.colombianita.Colombianita.dto;

import java.util.List;
import java.math.BigDecimal;

public class PedidoRequestDTO {
    
    private Long idSucursal;
    private BigDecimal total;
    private String tipoPedido; // 'WHATSAPP'
    private String estado; // 'PAGADO' o 'PENDIENTE'
    private List<DetallePedidoDTO> detalles;

    public PedidoRequestDTO() {
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }
}