package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Ingrediente;
import com.colombianita.Colombianita.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ingredientes")
@CrossOrigin(origins = "*")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }

    @PostMapping
    public Ingrediente crearIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    // 3. READ ONE: Buscar un ingrediente específico por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> obtenerIngredientePorId(@PathVariable Long id) {
        Optional<Ingrediente> ingrediente = ingredienteRepository.findById(id);
        if (ingrediente.isPresent()) {
            return ResponseEntity.ok(ingrediente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar un ingrediente existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> actualizarIngrediente(@PathVariable Long id, @RequestBody Ingrediente detallesIngrediente) {
        Optional<Ingrediente> ingredienteExistente = ingredienteRepository.findById(id);
        
        if (ingredienteExistente.isPresent()) {
            Ingrediente ingredienteAActualizar = ingredienteExistente.get();
            
            ingredienteAActualizar.setNombre(detallesIngrediente.getNombre());
            ingredienteAActualizar.setUnidadMedida(detallesIngrediente.getUnidadMedida());
            ingredienteAActualizar.setCostoUnitario(detallesIngrediente.getCostoUnitario());
            
            return ResponseEntity.ok(ingredienteRepository.save(ingredienteAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar un ingrediente (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngrediente(@PathVariable Long id) {
        if (ingredienteRepository.existsById(id)) {
            ingredienteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}