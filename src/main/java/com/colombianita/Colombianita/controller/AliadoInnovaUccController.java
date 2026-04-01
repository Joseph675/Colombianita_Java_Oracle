package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.AliadoInnovaUcc;
import com.colombianita.Colombianita.repository.AliadoInnovaUccRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/aliados")
@CrossOrigin(origins = "*")
public class AliadoInnovaUccController {

    @Autowired
    private AliadoInnovaUccRepository aliadoRepository;

    // 1. OBTENER SOLO LOS ALIADOS ACTIVOS (Para la vista pública del frontend)
    @GetMapping
    public List<AliadoInnovaUcc> listarAliadosActivos() {
        return aliadoRepository.findByActivo(1);
    }

    // 2. OBTENER UN ALIADO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<AliadoInnovaUcc> obtenerAliadoPorId(@PathVariable Long id) {
        Optional<AliadoInnovaUcc> aliado = aliadoRepository.findById(id);
        if (aliado.isPresent() && aliado.get().getActivo() == 1) {
            return ResponseEntity.ok(aliado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. CREAR UN NUEVO ALIADO
    @PostMapping
    public ResponseEntity<?> crearAliado(@RequestBody AliadoInnovaUcc aliado) {
        // Validamos que no exista otro aliado con el mismo nombre
        if (aliadoRepository.findByNombre(aliado.getNombre()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Ya existe un aliado con el nombre '" + aliado.getNombre() + "'.");
        }
        return ResponseEntity.ok(aliadoRepository.save(aliado));
    }

    // 4. ACTUALIZAR UN ALIADO EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<AliadoInnovaUcc> actualizarAliado(@PathVariable Long id, @RequestBody AliadoInnovaUcc detalles) {
        Optional<AliadoInnovaUcc> aliadoExistente = aliadoRepository.findById(id);
        
        if (aliadoExistente.isPresent()) {
            AliadoInnovaUcc aliado = aliadoExistente.get();
            aliado.setNombre(detalles.getNombre());
            aliado.setLogoUrl(detalles.getLogoUrl());
            aliado.setSitioWeb(detalles.getSitioWeb());
            aliado.setDescripcion(detalles.getDescripcion());
            // Guardamos los cambios
            return ResponseEntity.ok(aliadoRepository.save(aliado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR UN ALIADO (Borrado Lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAliado(@PathVariable Long id) {
        Optional<AliadoInnovaUcc> aliadoExistente = aliadoRepository.findById(id);
        
        if (aliadoExistente.isPresent()) {
            AliadoInnovaUcc aliado = aliadoExistente.get();
            // ¡Borrado lógico! Lo ocultamos cambiando el estado a 0
            aliado.setActivo(0);
            aliadoRepository.save(aliado);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}