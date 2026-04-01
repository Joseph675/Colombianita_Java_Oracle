package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.ProyectoImagenInnovaUcc;
import com.colombianita.Colombianita.repository.ProyectoImagenInnovaUccRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyecto-imagenes")
@CrossOrigin(origins = "*")
public class ProyectoImagenInnovaUccController {

    @Autowired
    private ProyectoImagenInnovaUccRepository imagenRepository;

    // 1. OBTENER TODAS LAS IMÁGENES DE UN PROYECTO (Para armar el carrusel)
    @GetMapping("/proyecto/{proyectoId}")
    public List<ProyectoImagenInnovaUcc> listarImagenesPorProyecto(@PathVariable Long proyectoId) {
        return imagenRepository.findByProyectoIdOrderByOrdenAsc(proyectoId);
    }

    // 2. OBTENER SOLO LA PORTADA DE UN PROYECTO
    @GetMapping("/proyecto/{proyectoId}/portada")
    public ResponseEntity<ProyectoImagenInnovaUcc> obtenerPortada(@PathVariable Long proyectoId) {
        // Buscamos donde es_principal = 1
        Optional<ProyectoImagenInnovaUcc> portada = imagenRepository.findByProyectoIdAndEsPrincipal(proyectoId, 1);
        
        if (portada.isPresent()) {
            return ResponseEntity.ok(portada.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. AGREGAR UNA NUEVA IMAGEN
    @PostMapping
    public ProyectoImagenInnovaUcc crearImagen(@RequestBody ProyectoImagenInnovaUcc imagen) {
        return imagenRepository.save(imagen);
    }

    // 4. ELIMINAR UNA IMAGEN ESPECÍFICA (Borrado Físico)
    // Aquí sí usamos borrado físico porque si el usuario quita una foto de su galería, no nos interesa guardarla.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarImagen(@PathVariable Long id) {
        if (imagenRepository.existsById(id)) {
            imagenRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}