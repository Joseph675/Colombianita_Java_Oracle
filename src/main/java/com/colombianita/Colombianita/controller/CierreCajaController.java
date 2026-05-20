package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.CierreCajaRequestDTO;
import com.colombianita.Colombianita.entity.CierreCaja;
import com.colombianita.Colombianita.repository.CierreCajaRepository;
import com.colombianita.Colombianita.service.CierreCajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cierres-caja")
@CrossOrigin(origins = "*")
public class CierreCajaController {

    @Autowired
    private CierreCajaRepository cierreCajaRepository;

    @Autowired
    private CierreCajaService cierreCajaService;

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
    public ResponseEntity<CierreCaja> procesarCierreCaja(@PathVariable Long id, @RequestBody CierreCajaRequestDTO requestDTO) {
        try {
            CierreCaja caja = cierreCajaService.procesarCierre(
                    id,
                    requestDTO.getEfectivoDeclarado(),
                    requestDTO.getObservaciones()
            );
            return ResponseEntity.ok(caja);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/recalcular")
    public ResponseEntity<CierreCaja> recalcularTotales(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cierreCajaService.recalcularTotales(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
