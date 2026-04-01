package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "convocatorias_innovaucc")
public class ConvocatoriaInnovaUcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "PROXIMA"; // PROXIMA, ABIERTA, CERRADA

    @Column(name = "fecha_apertura")
    private LocalDate fechaApertura;

    @Column(name = "fecha_cierre", nullable = false)
    private LocalDate fechaCierre;

    @Column(name = "premio", length = 200)
    private String premio;

    @Column(name = "cupos")
    private Integer cupos;

    // Relación con el aliado que patrocina la convocatoria
    @ManyToOne
    @JoinColumn(name = "aliado_id")
    private AliadoInnovaUcc aliado;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public ConvocatoriaInnovaUcc() {}

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDate fechaApertura) { this.fechaApertura = fechaApertura; }

    public LocalDate getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDate fechaCierre) { this.fechaCierre = fechaCierre; }

    public String getPremio() { return premio; }
    public void setPremio(String premio) { this.premio = premio; }

    public Integer getCupos() { return cupos; }
    public void setCupos(Integer cupos) { this.cupos = cupos; }

    public AliadoInnovaUcc getAliado() { return aliado; }
    public void setAliado(AliadoInnovaUcc aliado) { this.aliado = aliado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
}