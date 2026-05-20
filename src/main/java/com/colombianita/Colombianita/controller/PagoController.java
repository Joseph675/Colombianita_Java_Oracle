package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Pago;
import com.colombianita.Colombianita.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> listarPagos() {
        return pagoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestBody Pago pago) {
        return ResponseEntity.ok(pagoService.guardar(pago));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Long id) {
        return pagoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pedido/{idPedido}")
    public List<Pago> obtenerPagosPorPedido(@PathVariable Long idPedido) {
        return pagoService.buscarPorPedido(idPedido);
    }

    @GetMapping("/factura/{idFactura}")
    public List<Pago> obtenerPagosPorFactura(@PathVariable Long idFactura) {
        return pagoService.buscarPorFactura(idFactura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        if (!pagoService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        pago.setIdPago(id);
        return ResponseEntity.ok(pagoService.guardar(pago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        if (!pagoService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        pagoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
