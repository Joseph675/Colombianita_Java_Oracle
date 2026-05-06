package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.CierreCajaRequestDTO;
import com.colombianita.Colombianita.entity.CierreCaja;
import com.colombianita.Colombianita.repository.CierreCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<CierreCaja> procesarCierreCaja(@PathVariable Long id, @RequestBody CierreCajaRequestDTO requestDTO) {
        Optional<CierreCaja> cierreExistente = cierreCajaRepository.findById(id);
        
        if (cierreExistente.isPresent()) {
            CierreCaja caja = cierreExistente.get();
            caja.setFechaCierre(LocalDateTime.now());
            
            // Los totales (efectivo, tarjeta, etc.) ya no se reciben, pues el trigger los calcula en BD.
            // Solo recibimos el dinero que el cajero contó físicamente.
            caja.setEfectivoDeclarado(requestDTO.getEfectivoDeclarado());

            // La diferencia ahora se calcula en el backend para mayor seguridad.
            // Diferencia = (Dinero contado) - (Dinero que DEBERÍA haber según las facturas en efectivo)
            BigDecimal diferenciaCalculada = requestDTO.getEfectivoDeclarado().subtract(caja.getTotalEfectivo());
            caja.setDiferencia(diferenciaCalculada);
            caja.setEstado("CERRADA");
            caja.setObservaciones(requestDTO.getObservaciones());
            
            return ResponseEntity.ok(cierreCajaRepository.save(caja));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}