package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios_innovaucc")
public class UsuarioInnovaucc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "rol", nullable = false, length = 20)
    private String rol = "EMPRENDEDOR";

    @Column(name = "programa", length = 150)
    private String programa;

    @ManyToOne
    @JoinColumn(name = "sede_id")
    private SedeInnovaucc sede;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "foto_url", length = 500)
    private String fotoUrl;

    @Column(name = "iniciales", length = 5)
    private String iniciales;

    @Column(name = "activo", nullable = false)
    private Integer activo = 1;

    @Column(name = "fecha_registro", nullable = false, columnDefinition = "TIMESTAMP DEFAULT SYSTIMESTAMP")
    private LocalDateTime fechaRegistro;

    public UsuarioInnovaucc() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }

    public SedeInnovaucc getSede() { return sede; }
    public void setSede(SedeInnovaucc sede) { this.sede = sede; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public String getIniciales() { return iniciales; }
    public void setIniciales(String iniciales) { this.iniciales = iniciales; }

    public Integer getActivo() { return activo; }
    public void setActivo(Integer activo) { this.activo = activo; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
