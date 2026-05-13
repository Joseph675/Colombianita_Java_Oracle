package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.AlertaInventario;
import com.colombianita.Colombianita.repository.AlertaInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "*")
public class AlertaInventarioController {

    @Autowired
    private AlertaInventarioRepository alertaRepository;

    // 1. GET /api/alertas - Listar todas las alertas
    @GetMapping
    public List<AlertaInventario> listarAlertas() {
        return alertaRepository.findAll();
    }

    // GET /api/alertas/no-leidas - Listar todas las alertas globales no leídas
    @GetMapping("/no-leidas")
    public ResponseEntity<List<AlertaInventario>> listarTodasNoLeidas() {
        List<AlertaInventario> alertas = alertaRepository.findByEstadoOrderByFechaAlertaDesc("NO_LEIDA");
        return ResponseEntity.ok(alertas);
    }

    // 2. GET /api/alertas/sucursal/{id} - Listar todas las alertas de una sucursal
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<AlertaInventario>> listarAlertasPorSucursal(@PathVariable Long idSucursal) {
        List<AlertaInventario> alertas = alertaRepository.findBySucursalIdSucursalOrderByFechaAlertaDesc(idSucursal);
        return ResponseEntity.ok(alertas);
    }

    // 3. GET /api/alertas/sucursal/{id}/no-leidas - Para alimentar el contador de la campanita en Angular
    @GetMapping("/sucursal/{idSucursal}/no-leidas")
    public ResponseEntity<List<AlertaInventario>> listarNoLeidasPorSucursal(@PathVariable Long idSucursal) {
        List<AlertaInventario> alertas = alertaRepository.findBySucursalIdSucursalAndEstadoOrderByFechaAlertaDesc(idSucursal, "NO_LEIDA");
        return ResponseEntity.ok(alertas);
    }

    // PUT /api/alertas/marcar-leidas - Marcar TODAS las alertas globales como leídas
    @PutMapping("/marcar-leidas")
    public ResponseEntity<Void> marcarTodasComoLeidasGlobal() {
        List<AlertaInventario> alertasNoLeidas = alertaRepository.findByEstadoOrderByFechaAlertaDesc("NO_LEIDA");
        for (AlertaInventario alerta : alertasNoLeidas) {
            alerta.setEstado("LEIDA");
        }
        alertaRepository.saveAll(alertasNoLeidas);
        return ResponseEntity.ok().build();
    }

    // 4. POST /api/alertas - Crear una nueva alerta manualmente (o desde un servicio automático)
    @PostMapping
    public ResponseEntity<AlertaInventario> crearAlerta(@RequestBody AlertaInventario alerta) {
        return ResponseEntity.ok(alertaRepository.save(alerta));
    }

    // 5. PATCH /api/alertas/{id}/marcar-leida - Marcar una alerta específica como "LEIDA"
    @PatchMapping("/{id}/marcar-leida")
    public ResponseEntity<AlertaInventario> marcarComoLeida(@PathVariable Long id) {
        Optional<AlertaInventario> alertaExistente = alertaRepository.findById(id);
        
        if (alertaExistente.isPresent()) {
            AlertaInventario alerta = alertaExistente.get();
            alerta.setEstado("LEIDA");
            return ResponseEntity.ok(alertaRepository.save(alerta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // 6. PATCH /api/alertas/sucursal/{id}/marcar-todas-leidas - Marcar todas las notificaciones de una sucursal como leídas
    @PatchMapping("/sucursal/{idSucursal}/marcar-todas-leidas")
    public ResponseEntity<Void> marcarTodasComoLeidasPorSucursal(@PathVariable Long idSucursal) {
        List<AlertaInventario> alertasNoLeidas = alertaRepository.findBySucursalIdSucursalAndEstadoOrderByFechaAlertaDesc(idSucursal, "NO_LEIDA");
        for (AlertaInventario alerta : alertasNoLeidas) {
            alerta.setEstado("LEIDA");
        }
        alertaRepository.saveAll(alertasNoLeidas);
        return ResponseEntity.ok().build();
    }

    // 7. DELETE /api/alertas/{id} - Eliminar una alerta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlerta(@PathVariable Long id) {
        if (alertaRepository.existsById(id)) {
            alertaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}