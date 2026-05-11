package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.GastoCaja;
import com.colombianita.Colombianita.repository.GastoCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gastos-caja")
@CrossOrigin(origins = "*")
public class GastoCajaController {

    @Autowired
    private GastoCajaRepository gastoCajaRepository;

    @GetMapping
    public List<GastoCaja> listarGastos() {
        return gastoCajaRepository.findAll();
    }

    @PostMapping
    public GastoCaja registrarGasto(@RequestBody GastoCaja gastoCaja) {
        return gastoCajaRepository.save(gastoCaja);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoCaja> obtenerGastoPorId(@PathVariable Long id) {
        Optional<GastoCaja> gasto = gastoCajaRepository.findById(id);
        return gasto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para consultar los gastos de un cierre de caja específico
    @GetMapping("/cierre/{idCierre}")
    public ResponseEntity<List<GastoCaja>> obtenerGastosPorCierre(@PathVariable Long idCierre) {
        List<GastoCaja> gastos = gastoCajaRepository.findByCierreCajaIdCierre(idCierre);
        return ResponseEntity.ok(gastos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GastoCaja> actualizarGasto(@PathVariable Long id, @RequestBody GastoCaja detallesGasto) {
        Optional<GastoCaja> gastoExistente = gastoCajaRepository.findById(id);

        if (gastoExistente.isPresent()) {
            GastoCaja gastoAActualizar = gastoExistente.get();
            gastoAActualizar.setMonto(detallesGasto.getMonto());
            gastoAActualizar.setDescripcion(detallesGasto.getDescripcion());
            // No solemos actualizar ni el cierre ni el usuario creador por temas de auditoría, 
            // pero podrías agregarlo aquí si tu lógica de negocio lo requiere.

            return ResponseEntity.ok(gastoCajaRepository.save(gastoAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long id) {
        if (gastoCajaRepository.existsById(id)) {
            gastoCajaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}