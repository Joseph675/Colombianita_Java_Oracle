package com.colombianita.Colombianita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias_innovaucc")
public class CategoriaInnovaucc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 80, unique = true)
    private String nombre;

    @Column(name = "emoji", length = 10)
    private String emoji;

    @Column(name = "color_thumb", length = 30)
    private String colorThumb;

    @Column(name = "activa", nullable = false)
    private Integer activa = 1;

    public CategoriaInnovaucc() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public String getColorThumb() { return colorThumb; }
    public void setColorThumb(String colorThumb) { this.colorThumb = colorThumb; }

    public Integer getActiva() { return activa; }
    public void setActiva(Integer activa) { this.activa = activa; }
}
