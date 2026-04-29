package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Mesa;
import com.colombianita.Colombianita.entity.Pedido;
import com.colombianita.Colombianita.repository.MesaRepository;
import com.colombianita.Colombianita.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "*")
public class MesaController {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // 1. READ ALL: Obtener todas las mesas
    @GetMapping
    public List<Mesa> listarMesas() {
        return mesaRepository.findAll();
    }

    // 2. CREATE: Crear una nueva mesa
    @PostMapping
    public ResponseEntity<?> crearMesa(@RequestBody Mesa mesa) {
        // Verificamos si ya existe ese número de mesa para la sucursal indicada
        if (mesa.getSucursal() != null && mesa.getSucursal().getIdSucursal() != null) {
            if (mesaRepository.existsByNumeroMesaAndSucursalIdSucursal(mesa.getNumeroMesa(), mesa.getSucursal().getIdSucursal())) {
                return ResponseEntity.badRequest().body("Error: Ya existe la mesa número " + mesa.getNumeroMesa() + " en esta sucursal.");
            }
        }
        
        Mesa nuevaMesa = mesaRepository.save(mesa);
        return ResponseEntity.ok(nuevaMesa);
    }

    // 3. READ ONE: Obtener una mesa específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtenerMesaPorId(@PathVariable Long id) {
        Optional<Mesa> mesa = mesaRepository.findById(id);
        if (mesa.isPresent()) {
            return ResponseEntity.ok(mesa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar los datos de una mesa existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMesa(@PathVariable Long id, @RequestBody Mesa detallesMesa) {
        Optional<Mesa> mesaExistente = mesaRepository.findById(id);
        
        if (mesaExistente.isPresent()) {
            Mesa mesaAActualizar = mesaExistente.get();

            // Validar duplicado si está cambiando el número de mesa o cambiándola de sucursal
            boolean cambioNumero = !mesaAActualizar.getNumeroMesa().equals(detallesMesa.getNumeroMesa());
            boolean cambioSucursal = !mesaAActualizar.getSucursal().getIdSucursal().equals(detallesMesa.getSucursal().getIdSucursal());
            
            if (cambioNumero || cambioSucursal) {
                if (mesaRepository.existsByNumeroMesaAndSucursalIdSucursal(detallesMesa.getNumeroMesa(), detallesMesa.getSucursal().getIdSucursal())) {
                    return ResponseEntity.badRequest().body("Error: Ya existe la mesa número " + detallesMesa.getNumeroMesa() + " en esta sucursal.");
                }
            }

            mesaAActualizar.setSucursal(detallesMesa.getSucursal());
            mesaAActualizar.setNumeroMesa(detallesMesa.getNumeroMesa());
            mesaAActualizar.setCapacidad(detallesMesa.getCapacidad());
            mesaAActualizar.setEstado(detallesMesa.getEstado());
            
            return ResponseEntity.ok(mesaRepository.save(mesaAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar una mesa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Long id) {
        if (mesaRepository.existsById(id)) {
            mesaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. READ: Ver el historial de pedidos de una mesa específica
    @GetMapping("/{id}/historial")
    public ResponseEntity<List<Pedido>> obtenerHistorialPedidosMesa(@PathVariable("id") Long idMesa) {
        /*
         * Para que este método funcione y traiga los detalles de cada pedido,
         * necesitas agregar un método en tu PedidoRepository.java.
         *
         * (Verás el código para el repositorio en el Paso 4)
        */
        List<Pedido> historial = pedidoRepository.findByIdMesaConDetalles(idMesa);
        if (historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historial);
    }
}