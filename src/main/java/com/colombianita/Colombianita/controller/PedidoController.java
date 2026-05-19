package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.PedidoRequestDTO;
import com.colombianita.Colombianita.dto.DetallePedidoDTO;
import com.colombianita.Colombianita.entity.Pedido;
import com.colombianita.Colombianita.entity.DetallePedido;
import com.colombianita.Colombianita.entity.Sucursal;
import com.colombianita.Colombianita.entity.Usuario;
import com.colombianita.Colombianita.entity.PresentacionProducto;
import com.colombianita.Colombianita.entity.Cliente;
import com.colombianita.Colombianita.repository.PedidoRepository;
import com.colombianita.Colombianita.repository.DetallePedidoRepository;
import com.colombianita.Colombianita.repository.SucursalRepository;
import com.colombianita.Colombianita.repository.PresentacionProductoRepository;
import com.colombianita.Colombianita.repository.UsuarioRepository;
import com.colombianita.Colombianita.repository.MesaRepository;
import com.colombianita.Colombianita.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// PATRÓN: Facade — este controller orquesta múltiples subsistemas (repositorios de pedido,
//   cliente, mesa, detalle, presentación) detrás de una interfaz HTTP simple.
//   El caller (n8n / Angular) no sabe cuántos repositorios se coordinan internamente.
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

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    // PATRÓN: Facade — punto de entrada unificado que internamente:
    //   1) hace UPSERT del cliente (busca por whatsappId → celular → crea nuevo)
    //   2) crea el Pedido principal
    //   3) persiste cada DetallePedido
    //   4) transiciona el estado de la Mesa a OCUPADA (patrón State)
    // Todo esto ocurre en una sola transacción (@Transactional = patrón Proxy de Spring).
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
        nuevoPedido.setDireccionEntrega(requestDTO.getDireccionEntrega());
        if (requestDTO.getValorAdicional() != null)
            nuevoPedido.setValorAdicional(requestDTO.getValorAdicional());
        if (requestDTO.getNotaAdicional() != null)
            nuevoPedido.setNotaAdicional(requestDTO.getNotaAdicional());

        // ==========================================
        // 🚀 INICIO DEL UPSERT DEL CLIENTE (n8n)
        // ==========================================
        if (requestDTO.getCliente() != null) {
            String celularRecibido = requestDTO.getCliente().getCelular();
            String nombreRecibido = requestDTO.getCliente().getNombres(); // Asegúrate de que el DTO tenga getNombres()
            String whatsappIdRecibido = requestDTO.getCliente().getWhatsappId();
            String direccionRecibida = requestDTO.getDireccionEntrega();

            // 1. Buscamos primero por whatsappId (el ancla invariable de n8n)
            Optional<Cliente> clienteExistente = Optional.empty();
            if (whatsappIdRecibido != null && !whatsappIdRecibido.trim().isEmpty()) {
                clienteExistente = clienteRepository.findByWhatsappId(whatsappIdRecibido);
            }

            // 2. Fallback: si no lo encuentra por whatsappId, buscamos por celular (para
            // clientes antiguos en BD)
            if (!clienteExistente.isPresent() && celularRecibido != null && !celularRecibido.trim().isEmpty()) {
                clienteExistente = clienteRepository.findByCelular(celularRecibido);
            }

            Cliente clienteDelPedido;
            if (clienteExistente.isPresent()) {
                System.out.println("🟢 Cliente recurrente encontrado en la base de datos.");
                clienteDelPedido = clienteExistente.get();

                // Bandera para saber si hubo cambios y necesitamos hacer un UPDATE en la BD
                boolean necesitaActualizacion = false;

                // Actualizamos el nombre si nos envían uno nuevo
                if (nombreRecibido != null && !nombreRecibido.trim().isEmpty()
                        && !nombreRecibido.equals(clienteDelPedido.getNombres())) {
                    clienteDelPedido.setNombres(nombreRecibido);
                    necesitaActualizacion = true;
                }

                // Actualizamos el celular si n8n nos envía el real y es diferente al guardado
                // temporalmente
                if (celularRecibido != null && !celularRecibido.trim().isEmpty()
                        && !celularRecibido.equals(clienteDelPedido.getCelular())) {
                    clienteDelPedido.setCelular(celularRecibido);
                    necesitaActualizacion = true;
                }

                // Actualizamos la dirección predeterminada si la del nuevo pedido es diferente
                // a la guardada
                if (direccionRecibida != null && !direccionRecibida.trim().isEmpty()
                        && !direccionRecibida.equals(clienteDelPedido.getDireccionPredeterminada())) {
                    clienteDelPedido.setDireccionPredeterminada(direccionRecibida);
                    necesitaActualizacion = true;
                }

                // Asignamos el whatsapp_id SOLO si estaba nulo (por si lo encontramos por el
                // fallback de celular)
                if (whatsappIdRecibido != null && !whatsappIdRecibido.trim().isEmpty()
                        && clienteDelPedido.getWhatsappId() == null) {
                    clienteDelPedido.setWhatsappId(whatsappIdRecibido);
                    necesitaActualizacion = true;
                }

                if (necesitaActualizacion) {
                    clienteDelPedido = clienteRepository.save(clienteDelPedido);
                    System.out.println("🔄 Información del cliente actualizada con éxito.");
                }
            } else {
                System.out.println("� Cliente nuevo detectado. Creando registro en BD...");
                Cliente nuevoCliente = new Cliente();
                nuevoCliente.setCelular(celularRecibido);
                nuevoCliente.setNombres(nombreRecibido);
                nuevoCliente.setDireccionPredeterminada(direccionRecibida); // Guardamos su primera dirección
                nuevoCliente.setWhatsappId(whatsappIdRecibido); // Guardamos su ID de WhatsApp desde el principio

                // Guardamos y Oracle le asigna el ID automáticamente
                clienteDelPedido = clienteRepository.save(nuevoCliente);
            }

            // Asignamos el cliente (ya sea el viejo o el recién creado) al pedido
            nuevoPedido.setCliente(clienteDelPedido);

        } else if (requestDTO.getIdCliente() != null) {
            // 🛡️ Fallback: Por si en el futuro haces pedidos manuales desde tu frontend en
            // Angular enviando el ID
            Optional<Cliente> clienteOpt = clienteRepository.findById(requestDTO.getIdCliente());
            clienteOpt.ifPresent(nuevoPedido::setCliente);
        }
        // ==========================================
        // FIN DEL UPSERT DEL CLIENTE
        // ==========================================

        // Asignar la mesa si el pedido es de tipo 'MESA'
        if ("MESA".equalsIgnoreCase(requestDTO.getTipoPedido()) && requestDTO.getIdMesa() != null) {
            nuevoPedido.setIdMesa(requestDTO.getIdMesa());
        }

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
                    detalle.setNotas(detalleDTO.getNotas());

                    detallePedidoRepository.save(detalle);
                } else {
                    System.out.println("🔴 ERROR SILENCIOSO: El producto con ID " + detalleDTO.getIdPresentacion()
                            + " no existe en la tabla PRESENTACION_PRODUCTO.");
                }
            }
        } else {
            System.out.println("🔴 ERROR DE MAPEO: La lista de detalles llegó NULA desde n8n.");
        }

        // PATRÓN: State — transición explícita del estado de la Mesa: LIBRE → OCUPADA
        if ("MESA".equalsIgnoreCase(pedidoGuardado.getTipoPedido()) && pedidoGuardado.getIdMesa() != null) {
            Optional<com.colombianita.Colombianita.entity.Mesa> mesaOpt = mesaRepository
                    .findById(pedidoGuardado.getIdMesa());
            if (mesaOpt.isPresent()) {
                com.colombianita.Colombianita.entity.Mesa mesa = mesaOpt.get();
                mesa.setEstado("OCUPADA");
                mesaRepository.save(mesa);
            } else {
                // Si la mesa no existe, la transacción debe fallar.
                // Lanzar una excepción de runtime asegura que @Transactional haga un rollback.
                throw new RuntimeException("Error Crítico: La mesa con ID " + pedidoGuardado.getIdMesa()
                        + " no fue encontrada. El pedido ha sido cancelado.");
            }
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
    @Transactional
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id,
            @RequestBody PedidoRequestDTO detallesPedido) {
        Optional<Pedido> pedidoExistente = pedidoRepository.findById(id);

        if (!pedidoExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Pedido pedidoAActualizar = pedidoExistente.get();

        // 1. Campos simples
        if (detallesPedido.getEstado() != null)
            pedidoAActualizar.setEstado(detallesPedido.getEstado());
        if (detallesPedido.getDireccionEntrega() != null)
            pedidoAActualizar.setDireccionEntrega(detallesPedido.getDireccionEntrega());
        if (detallesPedido.getTotal() != null)
            pedidoAActualizar.setTotal(detallesPedido.getTotal());
        if (detallesPedido.getValorAdicional() != null)
            pedidoAActualizar.setValorAdicional(detallesPedido.getValorAdicional());
        if (detallesPedido.getNotaAdicional() != null)
            pedidoAActualizar.setNotaAdicional(detallesPedido.getNotaAdicional());

        // 2. Repartidor (lógica que ya tenías)
        if (detallesPedido.getRepartidor() != null && detallesPedido.getRepartidor().getIdUsuario() != null) {
            Usuario repartidor = usuarioRepository.findById(detallesPedido.getRepartidor().getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Repartidor no encontrado"));
            pedidoAActualizar.setRepartidor(repartidor);
        }

        // 3. Cliente
        if (detallesPedido.getCliente() != null && detallesPedido.getCliente().getIdCliente() != null) {
            clienteRepository.findById(detallesPedido.getCliente().getIdCliente())
                    .ifPresent(pedidoAActualizar::setCliente);
        }

        // 4. *** DETALLES — el núcleo del fix ***
        if (detallesPedido.getDetalles() != null) {
            for (DetallePedidoDTO dto : detallesPedido.getDetalles()) {

                if (dto.getIdDetalle() != null) {
                    // --- Detalle EXISTENTE: actualizar fraccion, precio, notas ---
                    detallePedidoRepository.findById(dto.getIdDetalle()).ifPresent(d -> {
                        d.setFraccion(dto.getFraccion());
                        d.setPrecioCobrado(dto.getPrecioCobrado());
                        d.setNotas(dto.getNotas());
                        detallePedidoRepository.save(d);
                    });
                } else {
                    // --- Detalle NUEVO: insertar ---
                    presentacionRepository.findById(dto.getIdPresentacion()).ifPresent(presentacion -> {
                        DetallePedido nuevo = new DetallePedido();
                        nuevo.setPedido(pedidoAActualizar);
                        nuevo.setPresentacion(presentacion);
                        nuevo.setFraccion(dto.getFraccion());
                        nuevo.setPrecioCobrado(dto.getPrecioCobrado());
                        nuevo.setNotas(dto.getNotas());
                        detallePedidoRepository.save(nuevo);
                    });
                }
            }
        }

        return ResponseEntity.ok(pedidoRepository.save(pedidoAActualizar));
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

    // 6. READ: Obtener pedidos activos en mesas (mesas ocupadas y qué piden)
    @GetMapping("/mesas/ocupadas")
    public ResponseEntity<List<Pedido>> obtenerPedidosActivosDeMesas() {
        /*
         * Este método requiere que agregues una función en tu PedidoRepository
         * para buscar pedidos de tipo 'MESA' con estado diferente a 'PAGADO'
         * y que además cargue sus detalles para evitar N+1 queries.
         *
         * (Verás el código para el repositorio en el Paso 4)
         */
        List<Pedido> pedidosActivos = pedidoRepository.findPedidosActivosDeMesaConDetalles();
        if (pedidosActivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidosActivos);
    }
}