package com.colombianita.Colombianita.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "presentacion_producto")
public class PresentacionProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presentacion")
    private Long idPresentacion;

    // Relación: Muchas presentaciones pertenecen a un solo Producto
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "nombre_presentacion", nullable = false, length = 50)
    private String nombrePresentacion;

    // ¡Aquí está el famoso PRECIO! Usamos BigDecimal para manejar dinero con precisión exacta
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    @Column(name = "estado")
    private Integer estado = 1; // 1 = Disponible, 0 = Inactivo

    // Y sus Getters y Setters:
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }

    public PresentacionProducto() {}

    public Long getIdPresentacion() { return idPresentacion; }
    public void setIdPresentacion(Long idPresentacion) { this.idPresentacion = idPresentacion; }
    
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    
    public String getNombrePresentacion() { return nombrePresentacion; }
    public void setNombrePresentacion(String nombrePresentacion) { this.nombrePresentacion = nombrePresentacion; }
    
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}