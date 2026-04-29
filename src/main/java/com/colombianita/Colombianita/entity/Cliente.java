package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "celular", nullable = false, unique = true, length = 20)
    private String celular;

    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "direccion_predeterminada", length = 255)
    private String direccionPredeterminada;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "estado")
    private Integer estado = 1;

    public Cliente() {}

    // Genera automáticamente la fecha si no se envía, simulando el DEFAULT CURRENT_TIMESTAMP
    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }

    // Getters y Setters
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccionPredeterminada() {
        return direccionPredeterminada;
    }

    public void setDireccionPredeterminada(String direccionPredeterminada) {
        this.direccionPredeterminada = direccionPredeterminada;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}