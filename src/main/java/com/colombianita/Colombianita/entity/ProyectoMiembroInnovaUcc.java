package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "proyecto_miembros_innovaucc", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"proyecto_id", "usuario_id"})
})
public class ProyectoMiembroInnovaUcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Relación con el Proyecto
    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private ProyectoInnovaUcc proyecto;

    // Relación con el Usuario (El miembro del equipo)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioInnovaUcc usuario;

    @Column(name = "rol_en_proyecto", length = 80)
    private String rolEnProyecto = "COLABORADOR"; // Valor por defecto

    public ProyectoMiembroInnovaUcc() {}

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProyectoInnovaUcc getProyecto() { return proyecto; }
    public void setProyecto(ProyectoInnovaUcc proyecto) { this.proyecto = proyecto; }

    public UsuarioInnovaUcc getUsuario() { return usuario; }
    public void setUsuario(UsuarioInnovaUcc usuario) { this.usuario = usuario; }

    public String getRolEnProyecto() { return rolEnProyecto; }
    public void setRolEnProyecto(String rolEnProyecto) { this.rolEnProyecto = rolEnProyecto; }
}