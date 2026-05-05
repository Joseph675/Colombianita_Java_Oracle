package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Factura;
import com.colombianita.Colombianita.entity.Pedido;
import com.colombianita.Colombianita.repository.FacturaRepository;
import com.colombianita.Colombianita.repository.FacturaSpecification;
import com.colombianita.Colombianita.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Factura> generarFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaRepository.save(factura);
        
        // Al facturar, cambiamos automáticamente el estado del pedido a PAGADO
        if (factura.getPedido() != null && factura.getPedido().getIdPedido() != null) {
            Optional<Pedido> pedidoOpt = pedidoRepository.findById(factura.getPedido().getIdPedido());
            if (pedidoOpt.isPresent()) {
                Pedido pedido = pedidoOpt.get();
                pedido.setEstado("PAGADO");
                pedidoRepository.save(pedido);
            }
        }
        return ResponseEntity.ok(nuevaFactura);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable Long id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        return factura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Factura> obtenerFacturaPorPedido(@PathVariable Long idPedido) {
        Optional<Factura> factura = facturaRepository.findByPedidoIdPedido(idPedido);
        return factura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Endpoint dinámico para BI (Filtro por método de pago y rango de fechas)
    @GetMapping("/buscar")
    public ResponseEntity<List<Factura>> buscarFacturas(
            @RequestParam(required = false) String metodoPago,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        Specification<Factura> spec = Specification.where(FacturaSpecification.metodoPagoEquals(metodoPago))
                .and(FacturaSpecification.fechaEmisionBetween(fechaInicio, fechaFin));
        return ResponseEntity.ok(facturaRepository.findAll(spec));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anularFactura(@PathVariable Long id) {
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}