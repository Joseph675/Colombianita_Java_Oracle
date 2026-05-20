package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.CobrarRequestDTO;
import com.colombianita.Colombianita.dto.CobrarResponseDTO;
import com.colombianita.Colombianita.entity.*;
import com.colombianita.Colombianita.repository.*;
import com.colombianita.Colombianita.service.CierreCajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Orquesta el flujo completo de cobro en una sola transacción:
// actualizar pedido → crear factura → crear pagos.
// Si cualquier paso falla, todo hace rollback automáticamente.
@RestController
@RequestMapping("/api/cobrar")
@CrossOrigin(origins = "*")
public class CobrarController {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private FacturaRepository facturaRepository;
    @Autowired private PagoRepository pagoRepository;
    @Autowired private CierreCajaRepository cierreCajaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CierreCajaService cierreCajaService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cobrar(@RequestBody CobrarRequestDTO dto) {

        // Validaciones básicas
        if (dto.getIdPedido() == null) {
            return ResponseEntity.badRequest().body("El campo idPedido es obligatorio.");
        }
        if (dto.getPagos() == null || dto.getPagos().isEmpty()) {
            return ResponseEntity.badRequest().body("Debe incluir al menos una línea de pago.");
        }
        if (dto.getSubtotal() == null) {
            return ResponseEntity.badRequest().body("El campo subtotal es obligatorio.");
        }

        // 1. Cargar pedido
        Pedido pedido = pedidoRepository.findById(dto.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + dto.getIdPedido()));

        // Guardia: no cobrar un pedido que ya tiene factura
        if (facturaRepository.findByPedidoIdPedido(dto.getIdPedido()).isPresent()) {
            return ResponseEntity.badRequest().body("El pedido " + dto.getIdPedido() + " ya tiene una factura generada.");
        }

        // 2. Actualizar campos del pedido que el cajero pudo haber modificado
        if (dto.getTotal() != null)         pedido.setTotal(dto.getTotal());
        if (dto.getValorAdicional() != null) pedido.setValorAdicional(dto.getValorAdicional());
        if (dto.getNotaAdicional() != null)  pedido.setNotaAdicional(dto.getNotaAdicional());
        pedido.setEstado("PAGADO");
        pedidoRepository.save(pedido);

        // 3. Crear factura
        CierreCaja cierre = cierreCajaRepository.findById(dto.getIdCierre())
                .orElseThrow(() -> new RuntimeException("Cierre de caja no encontrado: " + dto.getIdCierre()));

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getIdUsuario()));

        BigDecimal impuestos = dto.getImpuestos() != null ? dto.getImpuestos() : BigDecimal.ZERO;

        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setCierreCaja(cierre);
        factura.setUsuario(usuario);
        factura.setSubtotal(dto.getSubtotal());
        factura.setImpuestos(impuestos);
        factura.setTotal(dto.getSubtotal().add(impuestos));
        factura.setNitCliente(dto.getNitCliente());
        factura.setRazonSocial(dto.getRazonSocial());
        // Si hay una sola línea de pago, guarda el método; si hay varias, es MIXTO
        factura.setMetodoPago(dto.getPagos().size() == 1
                ? dto.getPagos().get(0).getMetodoPago()
                : "MIXTO");

        Factura facturaGuardada = facturaRepository.save(factura);

        // 4. Crear una fila en pagos por cada línea del modal
        List<Pago> pagosCreados = new ArrayList<>();
        for (CobrarRequestDTO.PagoLineaDTO linea : dto.getPagos()) {
            Pago pago = new Pago();
            pago.setPedido(pedido);
            pago.setFactura(facturaGuardada);
            pago.setMonto(linea.getMonto());
            pago.setMetodoPago(linea.getMetodoPago());
            pago.setDescripcion(linea.getDescripcion());
            pagosCreados.add(pagoRepository.save(pago));
        }

        // Actualizar totales del turno en tiempo real para que el modal de cierre sea correcto
        cierreCajaService.recalcularTotales(dto.getIdCierre());

        CobrarResponseDTO response = new CobrarResponseDTO();
        response.setFactura(facturaGuardada);
        response.setPagos(pagosCreados);

        return ResponseEntity.ok(response);
    }
}
