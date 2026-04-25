package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.PresentacionProducto;
import com.colombianita.Colombianita.repository.PresentacionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/presentaciones")
@CrossOrigin(origins = "*")
public class PresentacionProductoController {

    @Autowired
    private PresentacionProductoRepository presentacionRepository;

    // 1. READ: Obtener todas las presentaciones (GET)
    @GetMapping
    public List<PresentacionProducto> listarPresentaciones() {
        return presentacionRepository.findAll();
    }

    // 2. CREATE: Guardar una nueva presentación (POST)
    @PostMapping
    public PresentacionProducto crearPresentacion(@RequestBody PresentacionProducto presentacion) {
        return presentacionRepository.save(presentacion);
    }

    // 3. READ ONE: Buscar una presentación específica por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<PresentacionProducto> obtenerPresentacionPorId(@PathVariable Long id) {
        Optional<PresentacionProducto> presentacion = presentacionRepository.findById(id);
        if (presentacion.isPresent()) {
            return ResponseEntity.ok(presentacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar una presentación existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<PresentacionProducto> actualizarPresentacion(@PathVariable Long id, @RequestBody PresentacionProducto detallesPresentacion) {
        Optional<PresentacionProducto> presentacionExistente = presentacionRepository.findById(id);
        
        if (presentacionExistente.isPresent()) {
            PresentacionProducto presentacionAActualizar = presentacionExistente.get();
            presentacionAActualizar.setProducto(detallesPresentacion.getProducto());
            presentacionAActualizar.setNombrePresentacion(detallesPresentacion.getNombrePresentacion());
            presentacionAActualizar.setPrecio(detallesPresentacion.getPrecio());
            presentacionAActualizar.setEstado(detallesPresentacion.getEstado());
            
            return ResponseEntity.ok(presentacionRepository.save(presentacionAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar una presentación (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPresentacion(@PathVariable Long id) {
        if (presentacionRepository.existsById(id)) {
            presentacionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}