package com.colombianita.Colombianita.dto;

import com.colombianita.Colombianita.entity.Rol;
import com.colombianita.Colombianita.entity.Sucursal;

public class UsuarioDTO {

    private Long idUsuario;
    private String nombres;
    private String email;
    private Integer estado;
    private Rol rol;
    private Sucursal sucursal;

    public UsuarioDTO() {}

    // Getters y Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
}