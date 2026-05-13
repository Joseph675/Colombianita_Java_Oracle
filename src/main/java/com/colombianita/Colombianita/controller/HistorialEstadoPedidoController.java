package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.HistorialEstadoPedido;
import com.colombianita.Colombianita.repository.HistorialEstadoPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-estados-pedido")
@CrossOrigin(origins = "*")
public class HistorialEstadoPedidoController {

    @Autowired
    private HistorialEstadoPedidoRepository historialRepository;

    // 1. GET: Listar todo el historial (Uso administrativo/Superusuario)
    @GetMapping
    public List<HistorialEstadoPedido> listarTodoElHistorial() {
        return historialRepository.findAll();
    }

    // 2. GET: Ver cómo han cambiado los estados de un solo pedido a lo largo del tiempo
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<HistorialEstadoPedido>> obtenerHistorialPorPedido(@PathVariable Long idPedido) {
        List<HistorialEstadoPedido> historial = historialRepository.findByPedidoIdPedidoOrderByFechaCambioDesc(idPedido);
        return ResponseEntity.ok(historial);
    }

    // 3. POST: Registrar un nuevo cambio de estado (Insertar a la auditoría)
    @PostMapping
    public ResponseEntity<HistorialEstadoPedido> guardarHistorial(@RequestBody HistorialEstadoPedido historial) {
        return ResponseEntity.ok(historialRepository.save(historial));
    }
    
}