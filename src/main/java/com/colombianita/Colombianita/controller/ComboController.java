package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Combo;
import com.colombianita.Colombianita.repository.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/combos")
@CrossOrigin(origins = "*")
public class ComboController {

    @Autowired
    private ComboRepository comboRepository;

    @GetMapping
    public List<Combo> listarCombos() {
        return comboRepository.findAll();
    }

    @PostMapping
    public Combo crearCombo(@RequestBody Combo combo) {
        return comboRepository.save(combo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Combo> obtenerComboPorId(@PathVariable Long id) {
        Optional<Combo> combo = comboRepository.findById(id);
        return combo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Combo> actualizarCombo(@PathVariable Long id, @RequestBody Combo detallesCombo) {
        Optional<Combo> comboExistente = comboRepository.findById(id);

        if (comboExistente.isPresent()) {
            Combo comboAActualizar = comboExistente.get();
            comboAActualizar.setNombre(detallesCombo.getNombre());
            comboAActualizar.setDescripcion(detallesCombo.getDescripcion());
            comboAActualizar.setPrecioFijo(detallesCombo.getPrecioFijo());
            comboAActualizar.setFechaInicio(detallesCombo.getFechaInicio());
            comboAActualizar.setFechaFin(detallesCombo.getFechaFin());
            comboAActualizar.setEstado(detallesCombo.getEstado());

            return ResponseEntity.ok(comboRepository.save(comboAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCombo(@PathVariable Long id) {
        if (comboRepository.existsById(id)) {
            comboRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}