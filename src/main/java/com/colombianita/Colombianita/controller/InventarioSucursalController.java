package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.InventarioSucursal;
import com.colombianita.Colombianita.repository.InventarioSucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "*")
public class InventarioSucursalController {

    @Autowired
    private InventarioSucursalRepository inventarioRepository;

    @GetMapping
    public List<InventarioSucursal> listarInventario() {
        return inventarioRepository.findAll();
    }

    @PostMapping
    public InventarioSucursal agregarInventario(@RequestBody InventarioSucursal inventario) {
        return inventarioRepository.save(inventario);
    }

    // READ ONE: Buscar un registro de inventario específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<InventarioSucursal> obtenerInventarioPorId(@PathVariable Long id) {
        Optional<InventarioSucursal> inventario = inventarioRepository.findById(id);
        if (inventario.isPresent()) {
            return ResponseEntity.ok(inventario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE: Modificar un registro de inventario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<InventarioSucursal> actualizarInventario(@PathVariable Long id, @RequestBody InventarioSucursal detallesInventario) {
        Optional<InventarioSucursal> inventarioExistente = inventarioRepository.findById(id);
        
        if (inventarioExistente.isPresent()) {
            InventarioSucursal inventarioAActualizar = inventarioExistente.get();
            
            inventarioAActualizar.setSucursal(detallesInventario.getSucursal());
            inventarioAActualizar.setIngrediente(detallesInventario.getIngrediente());
            inventarioAActualizar.setCantidadActual(detallesInventario.getCantidadActual());
            inventarioAActualizar.setCantidadMinima(detallesInventario.getCantidadMinima());
            
            return ResponseEntity.ok(inventarioRepository.save(inventarioAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar un registro de inventario (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Long id) {
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}