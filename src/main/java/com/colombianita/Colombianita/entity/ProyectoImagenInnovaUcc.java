package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "proyecto_imagenes_innovaucc")
public class ProyectoImagenInnovaUcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Relación con el proyecto al que pertenece la imagen
    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private ProyectoInnovaUcc proyecto;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "es_principal", nullable = false)
    private Integer esPrincipal = 0; // 1 = Portada, 0 = Galería normal

    @Column(name = "orden")
    private Integer orden = 0; // Para ordenar el carrusel de fotos

    public ProyectoImagenInnovaUcc() {}

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProyectoInnovaUcc getProyecto() { return proyecto; }
    public void setProyecto(ProyectoInnovaUcc proyecto) { this.proyecto = proyecto; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Integer esPrincipal) { this.esPrincipal = esPrincipal; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}