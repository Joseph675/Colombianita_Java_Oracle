package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.ProyectoInnovaUcc;
import com.colombianita.Colombianita.repository.ProyectoInnovaUccRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
public class ProyectoInnovaUccController {

    @Autowired
    private ProyectoInnovaUccRepository proyectoRepository;

    // 1. OBTENER SOLO LOS PROYECTOS ACTIVOS (No los borrados)
    @GetMapping
    public List<ProyectoInnovaUcc> listarProyectosActivos() {
        return proyectoRepository.findByActivo(1);
    }

    // 2. OBTENER UN PROYECTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoInnovaUcc> obtenerProyectoPorId(@PathVariable Long id) {
        Optional<ProyectoInnovaUcc> proyecto = proyectoRepository.findById(id);
        if (proyecto.isPresent() && proyecto.get().getActivo() == 1) {
            return ResponseEntity.ok(proyecto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. CREAR UN NUEVO PROYECTO
    @PostMapping
    public ProyectoInnovaUcc crearProyecto(@RequestBody ProyectoInnovaUcc proyecto) {
        return proyectoRepository.save(proyecto);
    }

    // 4. ELIMINAR UN PROYECTO (Borrado Lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        Optional<ProyectoInnovaUcc> proyectoExistente = proyectoRepository.findById(id);
        
        if (proyectoExistente.isPresent()) {
            ProyectoInnovaUcc proyecto = proyectoExistente.get();
            // Lo ocultamos cambiando el estado a 0
            proyecto.setActivo(0);
            proyectoRepository.save(proyecto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}