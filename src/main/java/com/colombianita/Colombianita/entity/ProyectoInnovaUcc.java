package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "proyectos_innovaucc")
public class ProyectoInnovaUcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    // @Lob le dice a Java y a Oracle que este es un campo de texto gigante (CLOB)
    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    // Relación con la tabla categorias_innovaucc
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaInnovaucc categoria;

    // Relación con el creador/dueño del proyecto
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioInnovaucc usuario;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "ACTIVO";

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "es_nuevo", nullable = false)
    private Integer esNuevo = 1; // 1 = Sí, 0 = No

    @Column(name = "activo", nullable = false)
    private Integer activo = 1; // 1 = Activo, 0 = Borrado lógico

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    public ProyectoInnovaUcc() {}

    // --- Magia de Auditoría Automática ---
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public CategoriaInnovaucc getCategoria() { return categoria; }
    public void setCategoria(CategoriaInnovaucc categoria) { this.categoria = categoria; }

    public UsuarioInnovaucc getUsuario() { return usuario; }
    public void setUsuario(UsuarioInnovaucc usuario) { this.usuario = usuario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public Integer getEsNuevo() { return esNuevo; }
    public void setEsNuevo(Integer esNuevo) { this.esNuevo = esNuevo; }

    public Integer getActivo() { return activo; }
    public void setActivo(Integer activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}