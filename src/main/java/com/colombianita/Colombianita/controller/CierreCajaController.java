package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.CierreCaja;
import com.colombianita.Colombianita.repository.CierreCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cierres-caja")
@CrossOrigin(origins = "*")
public class CierreCajaController {

    @Autowired
    private CierreCajaRepository cierreCajaRepository;

    @GetMapping
    public List<CierreCaja> listarCierres() {
        return cierreCajaRepository.findAll();
    }

    @PostMapping
    public CierreCaja crearCierre(@RequestBody CierreCaja cierreCaja) {
        return cierreCajaRepository.save(cierreCaja);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CierreCaja> obtenerCierrePorId(@PathVariable Long id) {
        Optional<CierreCaja> cierre = cierreCajaRepository.findById(id);
        return cierre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<CierreCaja> procesarCierreCaja(@PathVariable Long id, @RequestBody CierreCaja cierreActualizado) {
        Optional<CierreCaja> cierreExistente = cierreCajaRepository.findById(id);
        
        if (cierreExistente.isPresent()) {
            CierreCaja caja = cierreExistente.get();
            caja.setFechaCierre(LocalDateTime.now());
            
            caja.setTotalEfectivo(cierreActualizado.getTotalEfectivo());
            caja.setTotalTarjetas(cierreActualizado.getTotalTarjetas());
            caja.setTotalTransferencias(cierreActualizado.getTotalTransferencias());
            caja.setEfectivoDeclarado(cierreActualizado.getEfectivoDeclarado());
            caja.setDiferencia(cierreActualizado.getDiferencia());
            caja.setEstado("CERRADA");
            caja.setObservaciones(cierreActualizado.getObservaciones());
            
            return ResponseEntity.ok(cierreCajaRepository.save(caja));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}