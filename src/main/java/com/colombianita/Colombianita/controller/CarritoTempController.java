package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.CarritoTemp;
import com.colombianita.Colombianita.repository.CarritoTempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoTempController {

    @Autowired
    private CarritoTempRepository carritoTempRepository;

    // 1. POST /api/carrito/guardar
    @PostMapping("/guardar")
    public ResponseEntity<CarritoTemp> guardarCarrito(@RequestBody CarritoTemp carritoTemp) {
        return ResponseEntity.ok(carritoTempRepository.save(carritoTemp));
    }

    // 2. GET /api/carrito/{whatsappId}
    @GetMapping("/{whatsappId}")
    public ResponseEntity<CarritoTemp> obtenerCarrito(@PathVariable String whatsappId) {
        Optional<CarritoTemp> carrito = carritoTempRepository.findById(whatsappId);
        if (carrito.isPresent()) {
            return ResponseEntity.ok(carrito.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. PUT /api/carrito/actualizar
    @PutMapping("/actualizar")
    public ResponseEntity<CarritoTemp> actualizarCarrito(@RequestBody CarritoTemp detallesCarrito) {
        if (detallesCarrito.getWhatsappId() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<CarritoTemp> carritoExistente = carritoTempRepository.findById(detallesCarrito.getWhatsappId());

        if (carritoExistente.isPresent()) {
            CarritoTemp carritoAActualizar = carritoExistente.get();
            
            // Actualizamos los campos recibidos
            if (detallesCarrito.getEstado() != null) carritoAActualizar.setEstado(detallesCarrito.getEstado());
            if (detallesCarrito.getIdPresentacion() != null) carritoAActualizar.setIdPresentacion(detallesCarrito.getIdPresentacion());
            if (detallesCarrito.getNombreProducto() != null) carritoAActualizar.setNombreProducto(detallesCarrito.getNombreProducto());
            if (detallesCarrito.getPrecio() != null) carritoAActualizar.setPrecio(detallesCarrito.getPrecio());
            if (detallesCarrito.getCantidad() != null) carritoAActualizar.setCantidad(detallesCarrito.getCantidad());
            if (detallesCarrito.getNotas() != null) carritoAActualizar.setNotas(detallesCarrito.getNotas());
            if (detallesCarrito.getCarritoJson() != null) carritoAActualizar.setCarritoJson(detallesCarrito.getCarritoJson());
            
            return ResponseEntity.ok(carritoTempRepository.save(carritoAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. DELETE /api/carrito/{whatsappId}
    @DeleteMapping("/{whatsappId}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable String whatsappId) {
        if (carritoTempRepository.existsById(whatsappId)) {
            carritoTempRepository.deleteById(whatsappId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}