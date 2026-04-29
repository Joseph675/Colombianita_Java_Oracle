package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BUFFER_MENSAJES")
public class BufferMensaje {

    @Id
    @Column(name = "CELULAR", length = 20)
    private String celular;

    @Lob
    @Column(name = "MENSAJE_ACUMULADO")
    private String mensajeAcumulado;

    @Column(name = "ESTADO", length = 20)
    private String estado = "ESPERANDO";

    @Column(name = "ULTIMA_ACTUALIZACION")
    private Timestamp ultimaActualizacion;

    public BufferMensaje() {}

    @PrePersist
    protected void onCreate() {
        if (ultimaActualizacion == null) {
            ultimaActualizacion = new Timestamp(System.currentTimeMillis());
        }
    }

    // Getters y Setters
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getMensajeAcumulado() { return mensajeAcumulado; }
    public void setMensajeAcumulado(String mensajeAcumulado) { this.mensajeAcumulado = mensajeAcumulado; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Timestamp getUltimaActualizacion() { return ultimaActualizacion; }
    public void setUltimaActualizacion(Timestamp ultimaActualizacion) { this.ultimaActualizacion = ultimaActualizacion; }
}