package com.colombianita.Colombianita.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    // Relación 1 a 1 ya que un pedido pertenece a una única factura
    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_cierre", nullable = false)
    private CierreCaja cierreCaja;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "impuestos", precision = 10, scale = 2)
    private BigDecimal impuestos = BigDecimal.ZERO;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @Column(name = "nit_cliente", length = 50)
    private String nitCliente;

    @Column(name = "razon_social", length = 100)
    private String razonSocial;

    @JsonManagedReference("factura-pagos")
    @OneToMany(mappedBy = "factura", fetch = FetchType.EAGER)
    private List<Pago> pagos;

    public Factura() {}

    @PrePersist
    protected void onCreate() {
        if (this.fechaEmision == null) {
            this.fechaEmision = LocalDateTime.now();
        }
    }

    // Getters y Setters
    public Long getIdFactura() { return idFactura; }
    public void setIdFactura(Long idFactura) { this.idFactura = idFactura; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public CierreCaja getCierreCaja() { return cierreCaja; }
    public void setCierreCaja(CierreCaja cierreCaja) { this.cierreCaja = cierreCaja; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getImpuestos() { return impuestos; }
    public void setImpuestos(BigDecimal impuestos) { this.impuestos = impuestos; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getNitCliente() { return nitCliente; }
    public void setNitCliente(String nitCliente) { this.nitCliente = nitCliente; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public List<Pago> getPagos() { return pagos; }
    public void setPagos(List<Pago> pagos) { this.pagos = pagos; }
}