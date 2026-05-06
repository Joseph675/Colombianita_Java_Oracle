package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.*;
import com.colombianita.Colombianita.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired private ViewVentasDiaRepository ventasDiaRepo;
    @Autowired private ViewEstadoMesaRepository estadoMesaRepo;
    @Autowired private ViewPedidoActivoRepository pedidoActivoRepo;
    @Autowired private ViewTopProductoDiaRepository topProductoRepo;
    @Autowired private ViewAlertaInventarioRepository alertaInventarioRepo;
    @Autowired private ViewCajaTurnoActivoRepository cajaTurnoRepo;
    @Autowired private ViewClienteRankingRepository clienteRankingRepo;

    @GetMapping("/ventas-dia")
    public ResponseEntity<List<ViewVentasDia>> getVentasDia(@RequestParam(required = false) Long idSucursal) {
        LocalDate hoy = LocalDate.now();
        if (idSucursal != null) {
            return ResponseEntity.ok(ventasDiaRepo.findByIdSucursalAndFecha(idSucursal, hoy));
        }
        return ResponseEntity.ok(ventasDiaRepo.findByFecha(hoy));
    }

    @GetMapping("/mesas")
    public ResponseEntity<List<ViewEstadoMesa>> getEstadoMesas(@RequestParam(required = false) Long idSucursal) {
        if (idSucursal != null) {
            return ResponseEntity.ok(estadoMesaRepo.findByIdSucursal(idSucursal));
        }
        return ResponseEntity.ok(estadoMesaRepo.findAll());
    }

    @GetMapping("/pedidos-activos")
    public ResponseEntity<List<ViewPedidoActivo>> getPedidosActivos(@RequestParam(required = false) Long idSucursal) {
        if (idSucursal != null) {
            return ResponseEntity.ok(pedidoActivoRepo.findByIdSucursal(idSucursal));
        }
        return ResponseEntity.ok(pedidoActivoRepo.findAll());
    }

    @GetMapping("/top-productos")
    public ResponseEntity<List<ViewTopProductoDia>> getTopProductos() {
        return ResponseEntity.ok(topProductoRepo.findAll());
    }

    @GetMapping("/alertas-inventario")
    public ResponseEntity<List<ViewAlertaInventario>> getAlertasInventario(@RequestParam(required = false) Long idSucursal) {
        if (idSucursal != null) {
            return ResponseEntity.ok(alertaInventarioRepo.findByIdSucursal(idSucursal));
        }
        return ResponseEntity.ok(alertaInventarioRepo.findAll());
    }

    @GetMapping("/caja-activa")
    public ResponseEntity<List<ViewCajaTurnoActivo>> getCajaActiva(@RequestParam(required = false) Long idSucursal) {
        if (idSucursal != null) {
            return ResponseEntity.ok(cajaTurnoRepo.findByIdSucursal(idSucursal));
        }
        return ResponseEntity.ok(cajaTurnoRepo.findAll());
    }

    @GetMapping("/clientes-ranking")
    public ResponseEntity<List<ViewClienteRanking>> getClientesRanking() {
        return ResponseEntity.ok(clienteRankingRepo.findAll());
    }
}