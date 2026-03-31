package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sedes_innovaucc")
public class SedeInnovaucc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "ciudad", nullable = false, length = 100)
    private String ciudad;

    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "activa", nullable = false)
    private Integer activa = 1;

    public SedeInnovaucc() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Integer getActiva() { return activa; }
    public void setActiva(Integer activa) { this.activa = activa; }
}
