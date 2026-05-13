package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.HistorialPrecio;
import com.colombianita.Colombianita.repository.HistorialPrecioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historial-precios")
@CrossOrigin(origins = "*")
public class HistorialPrecioController {

    @Autowired
    private HistorialPrecioRepository historialPrecioRepository;

    // 1. GET /api/historial-precios - Listar todo el historial general (Puede ser pesado, útil para paneles de admin)
    @GetMapping
    public List<HistorialPrecio> listarTodoElHistorial() {
        return historialPrecioRepository.findAll();
    }

    // 2. GET /api/historial-precios/{id} - Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<HistorialPrecio> obtenerPorId(@PathVariable Long id) {
        Optional<HistorialPrecio> historial = historialPrecioRepository.findById(id);
        return historial.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. GET /api/historial-precios/presentacion/{idPresentacion} - Ver cómo ha cambiado de precio un producto puntual
    @GetMapping("/presentacion/{idPresentacion}")
    public ResponseEntity<List<HistorialPrecio>> obtenerHistorialPorPresentacion(@PathVariable Long idPresentacion) {
        List<HistorialPrecio> historial = historialPrecioRepository.findByPresentacionIdPresentacionOrderByFechaCambioDesc(idPresentacion);
        return ResponseEntity.ok(historial);
    }
    
}