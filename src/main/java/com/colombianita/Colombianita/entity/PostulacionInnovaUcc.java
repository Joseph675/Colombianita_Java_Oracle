package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "postulaciones_innovaucc", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"convocatoria_id", "proyecto_id"})
})
public class PostulacionInnovaUcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Relación con la Convocatoria
    @ManyToOne
    @JoinColumn(name = "convocatoria_id", nullable = false)
    private ConvocatoriaInnovaUcc convocatoria;

    // Relación con el Proyecto que se postula
    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private ProyectoInnovaUcc proyecto;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "PENDIENTE"; // PENDIENTE, APROBADA, RECHAZADA

    @Column(name = "fecha_postulacion", nullable = false, updatable = false)
    private LocalDateTime fechaPostulacion;

    @Column(name = "nota", length = 1000)
    private String nota;

    @PrePersist
    protected void onCreate() {
        this.fechaPostulacion = LocalDateTime.now();
    }

    public PostulacionInnovaUcc() {}

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ConvocatoriaInnovaUcc getConvocatoria() { return convocatoria; }
    public void setConvocatoria(ConvocatoriaInnovaUcc convocatoria) { this.convocatoria = convocatoria; }

    public ProyectoInnovaUcc getProyecto() { return proyecto; }
    public void setProyecto(ProyectoInnovaUcc proyecto) { this.proyecto = proyecto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaPostulacion() { return fechaPostulacion; }
    public void setFechaPostulacion(LocalDateTime fechaPostulacion) { this.fechaPostulacion = fechaPostulacion; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }
}