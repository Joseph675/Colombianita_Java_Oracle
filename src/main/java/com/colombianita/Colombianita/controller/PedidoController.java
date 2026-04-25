package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.PedidoRequestDTO;
import com.colombianita.Colombianita.dto.DetallePedidoDTO;
import com.colombianita.Colombianita.entity.Pedido;
import com.colombianita.Colombianita.entity.DetallePedido;
import com.colombianita.Colombianita.entity.Sucursal;
import com.colombianita.Colombianita.entity.PresentacionProducto;
import com.colombianita.Colombianita.repository.PedidoRepository;
import com.colombianita.Colombianita.repository.DetallePedidoRepository;
import com.colombianita.Colombianita.repository.SucursalRepository;
import com.colombianita.Colombianita.repository.PresentacionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private PresentacionProductoRepository presentacionRepository;

    // 1. READ ALL: Obtener todos los pedidos
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // 2. CREATE: Guardar un nuevo pedido
    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // ENDPOINT PARA RECIBIR PEDIDOS DESDE n8n (BOT DE WHATSAPP)
    @PostMapping("/crear")
    @Transactional
    public ResponseEntity<?> crearPedidoCompleto(@RequestBody PedidoRequestDTO requestDTO) {

        // 1. Buscar la sucursal
        Optional<Sucursal> sucursalOpt = sucursalRepository.findById(requestDTO.getIdSucursal());
        if (!sucursalOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Error: Sucursal no encontrada");
        }

        // 2. Crear y guardar el Pedido principal
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setSucursal(sucursalOpt.get());
        nuevoPedido.setTotal(requestDTO.getTotal());
        nuevoPedido.setTipoPedido(requestDTO.getTipoPedido());
        nuevoPedido.setEstado(requestDTO.getEstado());
        nuevoPedido.setFechaHora(LocalDateTime.now()); // Asignamos la hora actual del servidor

        // Al guardar el pedido, Oracle genera automáticamente el ID
        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);

        // 3. Iterar sobre los detalles del DTO y guardar cada uno con el pedido
        // asociado
        if (requestDTO.getDetalles() != null) {
            System.out.println("✅ n8n envió " + requestDTO.getDetalles().size() + " productos en el carrito.");

            for (DetallePedidoDTO detalleDTO : requestDTO.getDetalles()) {
                System.out.println("🔍 Buscando en BD el producto con ID: " + detalleDTO.getIdPresentacion());

                Optional<PresentacionProducto> presentacionOpt = presentacionRepository
                        .findById(detalleDTO.getIdPresentacion());

                if (presentacionOpt.isPresent()) {
                    System.out.println("🟢 Producto encontrado. Guardando en DETALLE_PEDIDO...");
                    DetallePedido detalle = new DetallePedido();
                    detalle.setPedido(pedidoGuardado);
                    detalle.setPresentacion(presentacionOpt.get());
                    detalle.setFraccion(detalleDTO.getFraccion());
                    detalle.setPrecioCobrado(detalleDTO.getPrecioCobrado());

                    detallePedidoRepository.save(detalle);
                } else {
                    System.out.println("🔴 ERROR SILENCIOSO: El producto con ID " + detalleDTO.getIdPresentacion()
                            + " no existe en la tabla PRESENTACION_PRODUCTO.");
                }
            }
        } else {
            System.out.println("🔴 ERROR DE MAPEO: La lista de detalles llegó NULA desde n8n.");
        }

        return ResponseEntity.ok(pedidoGuardado);
    }

    // 3. READ ONE: Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Actualizar un pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido detallesPedido) {
        Optional<Pedido> pedidoExistente = pedidoRepository.findById(id);

        if (pedidoExistente.isPresent()) {
            Pedido pedidoAActualizar = pedidoExistente.get();
            pedidoAActualizar.setSucursal(detallesPedido.getSucursal());
            pedidoAActualizar.setFechaHora(detallesPedido.getFechaHora());
            pedidoAActualizar.setTotal(detallesPedido.getTotal());
            pedidoAActualizar.setTipoPedido(detallesPedido.getTipoPedido());
            pedidoAActualizar.setIdMesa(detallesPedido.getIdMesa());
            pedidoAActualizar.setEstado(detallesPedido.getEstado());

            return ResponseEntity.ok(pedidoRepository.save(pedidoAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}