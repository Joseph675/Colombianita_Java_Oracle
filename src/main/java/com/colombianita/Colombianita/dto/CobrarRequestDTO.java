package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;
import java.util.List;

public class CobrarRequestDTO {

    // Pedido a cobrar
    private Long idPedido;
    private BigDecimal total;
    private BigDecimal valorAdicional;
    private String notaAdicional;

    // Datos de la factura
    private Long idCierre;
    private Long idUsuario;
    private BigDecimal subtotal;
    private BigDecimal impuestos;
    private String nitCliente;
    private String razonSocial;

    // Líneas de pago (una o más)
    private List<PagoLineaDTO> pagos;

    public static class PagoLineaDTO {
        private String metodoPago;
        private BigDecimal monto;
        private String descripcion;

        public String getMetodoPago() { return metodoPago; }
        public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

        public BigDecimal getMonto() { return monto; }
        public void setMonto(BigDecimal monto) { this.monto = monto; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getValorAdicional() { return valorAdicional; }
    public void setValorAdicional(BigDecimal valorAdicional) { this.valorAdicional = valorAdicional; }

    public String getNotaAdicional() { return notaAdicional; }
    public void setNotaAdicional(String notaAdicional) { this.notaAdicional = notaAdicional; }

    public Long getIdCierre() { return idCierre; }
    public void setIdCierre(Long idCierre) { this.idCierre = idCierre; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getImpuestos() { return impuestos; }
    public void setImpuestos(BigDecimal impuestos) { this.impuestos = impuestos; }

    public String getNitCliente() { return nitCliente; }
    public void setNitCliente(String nitCliente) { this.nitCliente = nitCliente; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public List<PagoLineaDTO> getPagos() { return pagos; }
    public void setPagos(List<PagoLineaDTO> pagos) { this.pagos = pagos; }
}
