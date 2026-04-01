package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aliados_innovaucc")
public class AliadoInnovaUcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 150)
    private String nombre;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "sitio_web", length = 300)
    private String sitioWeb;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Integer activo = 1; // 1 = Activo, 0 = Inactivo (Borrado lógico)

    public AliadoInnovaUcc() {}

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getActivo() { return activo; }
    public void setActivo(Integer activo) { this.activo = activo; }
}