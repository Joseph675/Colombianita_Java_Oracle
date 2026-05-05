package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.RankingProductoDTO;
import com.colombianita.Colombianita.dto.TopClienteDTO;
import com.colombianita.Colombianita.entity.CierreCaja;
import com.colombianita.Colombianita.entity.InventarioSucursal;
import com.colombianita.Colombianita.repository.CierreCajaRepository;
import com.colombianita.Colombianita.repository.DetallePedidoRepository;
import com.colombianita.Colombianita.repository.InventarioSucursalRepository;
import com.colombianita.Colombianita.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private DetallePedidoRepository detallePedidoRepository;
    @Autowired private InventarioSucursalRepository inventarioRepository;
    @Autowired private CierreCajaRepository cierreCajaRepository;

    // Módulo Marketing: Top 10 Clientes
    @GetMapping("/clientes/top")
    public ResponseEntity<List<TopClienteDTO>> obtenerTopClientes() {
        // En un caso real podrías limitar el resultado a los primeros 10 en la consulta o con Pageable
        return ResponseEntity.ok(pedidoRepository.findTopClientes());
    }

    // Módulo Operativo: Ranking del Menú
    @GetMapping("/productos/ranking")
    public ResponseEntity<List<RankingProductoDTO>> obtenerRankingMenu() {
        return ResponseEntity.ok(detallePedidoRepository.findRankingProductos());
    }

    // Módulo Inventario: Alertas de Stock
    @GetMapping("/inventario/alertas/{idSucursal}")
    public ResponseEntity<List<InventarioSucursal>> obtenerAlertasDesabastecimiento(@PathVariable Long idSucursal) {
        return ResponseEntity.ok(inventarioRepository.findAlertasDesabastecimiento(idSucursal));
    }

    // Módulo Financiero: Descuadres Críticos
    @GetMapping("/auditoria/faltantes")
    public ResponseEntity<List<CierreCaja>> obtenerFaltantesCriticos(@RequestParam(defaultValue = "-5000") BigDecimal limite) {
        // Buscar faltantes de los últimos 30 días
        LocalDateTime hace30Dias = LocalDateTime.now().minusDays(30);
        return ResponseEntity.ok(cierreCajaRepository.findCierresConFaltanteCritico(limite, hace30Dias));
    }
}