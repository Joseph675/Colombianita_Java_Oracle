package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.CostoRecetaDTO;
import com.colombianita.Colombianita.entity.Receta;
import com.colombianita.Colombianita.entity.ViewRecetaDetalle;
import com.colombianita.Colombianita.repository.RecetaRepository;
import com.colombianita.Colombianita.repository.ViewRecetaDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private ViewRecetaDetalleRepository viewRecetaDetalleRepository;

    // 1. READ ALL: Obtener todas las recetas
    @GetMapping
    public List<Receta> listarRecetas() {
        return recetaRepository.findAll();
    }

    // 2. CREATE: Guardar un nuevo registro de receta (un ingrediente para una presentación)
    @PostMapping
    public Receta crearReceta(@RequestBody Receta receta) {
        return recetaRepository.save(receta);
    }

    // 3. READ ONE: Obtener un registro de receta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerRecetaPorId(@PathVariable Long id) {
        Optional<Receta> receta = recetaRepository.findById(id);
        if (receta.isPresent()) {
            return ResponseEntity.ok(receta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. READ BY PRESENTACION: Obtener todos los ingredientes de una presentación (JOIN FETCH evita N+1)
    @GetMapping("/presentacion/{idPresentacion}")
    public ResponseEntity<List<Receta>> obtenerRecetaPorPresentacion(@PathVariable Long idPresentacion) {
        List<Receta> ingredientes = recetaRepository.findConIngredienteByPresentacionId(idPresentacion);
        if (ingredientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ingredientes);
    }

    // 5. COSTO: Resumen de costo total de ingredientes por presentación
    @GetMapping("/presentacion/{idPresentacion}/costo")
    public ResponseEntity<CostoRecetaDTO> obtenerCostoPorPresentacion(@PathVariable Long idPresentacion) {
        CostoRecetaDTO costo = recetaRepository.calcularCostoPorPresentacion(idPresentacion);
        if (costo == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(costo);
    }

    // 6. DETALLE VISTA: Consulta directamente la vista v_receta_detalle (JOIN en DB, no en app)
    @GetMapping("/presentacion/{idPresentacion}/detalle")
    public ResponseEntity<List<ViewRecetaDetalle>> obtenerDetallePorPresentacion(@PathVariable Long idPresentacion) {
        List<ViewRecetaDetalle> detalle = viewRecetaDetalleRepository.findByIdPresentacion(idPresentacion);
        if (detalle.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalle);
    }

    // 5. UPDATE: Actualizar una cantidad o ingrediente en la receta
    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(@PathVariable Long id, @RequestBody Receta detallesReceta) {
        Optional<Receta> recetaExistente = recetaRepository.findById(id);
        
        if (recetaExistente.isPresent()) {
            Receta recetaAActualizar = recetaExistente.get();
            recetaAActualizar.setPresentacion(detallesReceta.getPresentacion());
            recetaAActualizar.setIngrediente(detallesReceta.getIngrediente());
            recetaAActualizar.setCantidadNecesaria(detallesReceta.getCantidadNecesaria());
            
            return ResponseEntity.ok(recetaRepository.save(recetaAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. DELETE: Eliminar un registro de la receta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id) {
        if (recetaRepository.existsById(id)) {
            recetaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}