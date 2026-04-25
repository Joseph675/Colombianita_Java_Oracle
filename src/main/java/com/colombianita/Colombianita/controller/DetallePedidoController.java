package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.DetallePedido;
import com.colombianita.Colombianita.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalles-pedido")
@CrossOrigin(origins = "*")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    // 1. READ ALL: Obtener todos los detalles de pedidos
    @GetMapping
    public List<DetallePedido> listarDetalles() {
        return detallePedidoRepository.findAll();
    }

    // 2. CREATE: Agregar un producto (detalle) a un pedido
    @PostMapping
    public DetallePedido crearDetalle(@RequestBody DetallePedido detalle) {
        return detallePedidoRepository.save(detalle);
    }

    // 3. READ ONE: Obtener un detalle específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtenerDetallePorId(@PathVariable Long id) {
        Optional<DetallePedido> detalle = detallePedidoRepository.findById(id);
        if (detalle.isPresent()) {
            return ResponseEntity.ok(detalle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar un detalle de pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizarDetalle(@PathVariable Long id, @RequestBody DetallePedido detallesNuevos) {
        Optional<DetallePedido> detalleExistente = detallePedidoRepository.findById(id);
        
        if (detalleExistente.isPresent()) {
            DetallePedido detalleAActualizar = detalleExistente.get();
            detalleAActualizar.setPedido(detallesNuevos.getPedido());
            detalleAActualizar.setPresentacion(detallesNuevos.getPresentacion());
            detalleAActualizar.setFraccion(detallesNuevos.getFraccion());
            detalleAActualizar.setPrecioCobrado(detallesNuevos.getPrecioCobrado());
            
            return ResponseEntity.ok(detallePedidoRepository.save(detalleAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar un detalle de pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        if (detallePedidoRepository.existsById(id)) {
            detallePedidoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}