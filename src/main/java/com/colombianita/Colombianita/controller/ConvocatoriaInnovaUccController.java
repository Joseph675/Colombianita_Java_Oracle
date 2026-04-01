package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.ConvocatoriaInnovaUcc;
import com.colombianita.Colombianita.repository.ConvocatoriaInnovaUccRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/convocatorias")
@CrossOrigin(origins = "*")
public class ConvocatoriaInnovaUccController {

    @Autowired
    private ConvocatoriaInnovaUccRepository convocatoriaRepository;

    // 1. LISTAR TODAS LAS CONVOCATORIAS
    @GetMapping
    public List<ConvocatoriaInnovaUcc> listarTodas() {
        return convocatoriaRepository.findAllByOrderByFechaCierreAsc();
    }

    // 2. LISTAR POR ESTADO (Útil para filtrar 'ABIERTA' en el frontend)
    @GetMapping("/estado/{estado}")
    public List<ConvocatoriaInnovaUcc> listarPorEstado(@PathVariable String estado) {
        return convocatoriaRepository.findByEstado(estado.toUpperCase());
    }

    // 3. OBTENER DETALLE DE UNA CONVOCATORIA
    @GetMapping("/{id}")
    public ResponseEntity<ConvocatoriaInnovaUcc> obtenerPorId(@PathVariable Long id) {
        Optional<ConvocatoriaInnovaUcc> convocatoria = convocatoriaRepository.findById(id);
        return convocatoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. CREAR NUEVA CONVOCATORIA
    @PostMapping
    public ConvocatoriaInnovaUcc crear(@RequestBody ConvocatoriaInnovaUcc convocatoria) {
        return convocatoriaRepository.save(convocatoria);
    }

    // 5. ELIMINAR CONVOCATORIA (Borrado Físico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (convocatoriaRepository.existsById(id)) {
            convocatoriaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}