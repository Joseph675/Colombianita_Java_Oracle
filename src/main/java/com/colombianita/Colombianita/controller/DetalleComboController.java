package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.DetalleCombo;
import com.colombianita.Colombianita.repository.DetalleComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles-combo")
@CrossOrigin(origins = "*")
public class DetalleComboController {

    @Autowired
    private DetalleComboRepository detalleComboRepository;

    @GetMapping
    public List<DetalleCombo> listarDetalles() {
        return detalleComboRepository.findAll();
    }

    @PostMapping
    public DetalleCombo crearDetalle(@RequestBody DetalleCombo detalle) {
        return detalleComboRepository.save(detalle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCombo> obtenerDetallePorId(@PathVariable Long id) {
        Optional<DetalleCombo> detalle = detalleComboRepository.findById(id);
        return detalle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener los productos que pertenecen a un combo en específico
    @GetMapping("/combo/{idCombo}")
    public ResponseEntity<List<DetalleCombo>> obtenerDetallesPorCombo(@PathVariable Long idCombo) {
        List<DetalleCombo> detalles = detalleComboRepository.findByComboIdCombo(idCombo);
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleCombo> actualizarDetalle(@PathVariable Long id, @RequestBody DetalleCombo detallesNuevos) {
        Optional<DetalleCombo> detalleExistente = detalleComboRepository.findById(id);

        if (detalleExistente.isPresent()) {
            DetalleCombo detalleAActualizar = detalleExistente.get();
            detalleAActualizar.setCombo(detallesNuevos.getCombo());
            detalleAActualizar.setPresentacion(detallesNuevos.getPresentacion());
            detalleAActualizar.setCantidad(detallesNuevos.getCantidad());

            return ResponseEntity.ok(detalleComboRepository.save(detalleAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        if (detalleComboRepository.existsById(id)) {
            detalleComboRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}