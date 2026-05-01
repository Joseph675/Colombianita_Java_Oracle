package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;
import java.util.List;

public class PedidoRequestDTO {
    private SucursalRef sucursal;
    private BigDecimal total;
    private String tipoPedido;
    private String estado;
    private MesaRef mesa; 
    private ClienteRef cliente;
    private String direccionEntrega;
    
    private List<DetallePedidoDTO> detalles;

    // Clases anidadas para recibir los objetos que envía Angular
    public static class SucursalRef {
        private Long idSucursal;
        public Long getIdSucursal() { return idSucursal; }
        public void setIdSucursal(Long idSucursal) { this.idSucursal = idSucursal; }
    }

    public static class MesaRef {
        private Long idMesa;
        public Long getIdMesa() { return idMesa; }
        public void setIdMesa(Long idMesa) { this.idMesa = idMesa; }
    }

    public static class ClienteRef {
        private Long idCliente;
        public Long getIdCliente() { return idCliente; }
        public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    }

    // Métodos auxiliares para que el Controller siga funcionando igual sin cambios
    public Long getIdSucursal() {
        return sucursal != null ? sucursal.getIdSucursal() : null;
    }

    public Long getIdMesa() {
        return mesa != null ? mesa.getIdMesa() : null;
    }

    public Long getIdCliente() {
        return cliente != null ? cliente.getIdCliente() : null;
    }

    // Getters y Setters de los objetos
    public SucursalRef getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalRef sucursal) {
        this.sucursal = sucursal;
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

    public MesaRef getMesa() {
        return mesa;
    }

    public void setMesa(MesaRef mesa) {
        this.mesa = mesa;
    }

    public ClienteRef getCliente() {
        return cliente;
    }

    public void setCliente(ClienteRef cliente) {
        this.cliente = cliente;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }
}