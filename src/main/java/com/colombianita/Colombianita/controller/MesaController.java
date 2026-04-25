package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Mesa;
import com.colombianita.Colombianita.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "*")
public class MesaController {

    @Autowired
    private MesaRepository mesaRepository;

    // 1. READ ALL: Obtener todas las mesas
    @GetMapping
    public List<Mesa> listarMesas() {
        return mesaRepository.findAll();
    }

    // 2. CREATE: Crear una nueva mesa
    @PostMapping
    public Mesa crearMesa(@RequestBody Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    // 3. READ ONE: Obtener una mesa específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtenerMesaPorId(@PathVariable Long id) {
        Optional<Mesa> mesa = mesaRepository.findById(id);
        if (mesa.isPresent()) {
            return ResponseEntity.ok(mesa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar los datos de una mesa existente
    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizarMesa(@PathVariable Long id, @RequestBody Mesa detallesMesa) {
        Optional<Mesa> mesaExistente = mesaRepository.findById(id);
        
        if (mesaExistente.isPresent()) {
            Mesa mesaAActualizar = mesaExistente.get();
            mesaAActualizar.setSucursal(detallesMesa.getSucursal());
            mesaAActualizar.setNumeroMesa(detallesMesa.getNumeroMesa());
            mesaAActualizar.setCapacidad(detallesMesa.getCapacidad());
            mesaAActualizar.setEstado(detallesMesa.getEstado());
            
            return ResponseEntity.ok(mesaRepository.save(mesaAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar una mesa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Long id) {
        if (mesaRepository.existsById(id)) {
            mesaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}