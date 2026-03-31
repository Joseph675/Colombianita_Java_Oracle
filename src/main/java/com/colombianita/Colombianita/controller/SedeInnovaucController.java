package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.SedeInnovaucc;
import com.colombianita.Colombianita.repository.SedeInnovaucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sedes-innovaucc")
@CrossOrigin(origins = "*")
public class SedeInnovaucController {

    @Autowired
    private SedeInnovaucRepository sedeInnovaucRepository;

    // 1. READ: Obtener todas las sedes (GET)
    @GetMapping
    public List<SedeInnovaucc> listarSedes() {
        return sedeInnovaucRepository.findAll();
    }

    // 2. CREATE: Guardar una nueva sede (POST)
    @PostMapping
    public SedeInnovaucc crearSede(@RequestBody SedeInnovaucc sede) {
        return sedeInnovaucRepository.save(sede);
    }

    // 3. READ ONE: Buscar una sede específica por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<SedeInnovaucc> obtenerSedePorId(@PathVariable Long id) {
        Optional<SedeInnovaucc> sede = sedeInnovaucRepository.findById(id);
        if (sede.isPresent()) {
            return ResponseEntity.ok(sede.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar una sede existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<SedeInnovaucc> actualizarSede(@PathVariable Long id, @RequestBody SedeInnovaucc detallesSede) {
        Optional<SedeInnovaucc> sedeExistente = sedeInnovaucRepository.findById(id);

        if (sedeExistente.isPresent()) {
            SedeInnovaucc sedeAActualizar = sedeExistente.get();
            sedeAActualizar.setNombre(detallesSede.getNombre());
            sedeAActualizar.setCiudad(detallesSede.getCiudad());
            sedeAActualizar.setDepartamento(detallesSede.getDepartamento());
            sedeAActualizar.setDireccion(detallesSede.getDireccion());
            sedeAActualizar.setActiva(detallesSede.getActiva());

            return ResponseEntity.ok(sedeInnovaucRepository.save(sedeAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar una sede (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSede(@PathVariable Long id) {
        if (sedeInnovaucRepository.existsById(id)) {
            sedeInnovaucRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
