package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Cliente;
import com.colombianita.Colombianita.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // 1. READ ALL: Obtener todos los clientes
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // 2. CREATE: Guardar un nuevo cliente (upsert por celular — evita 500 por duplicado)
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Optional<Cliente> existente = clienteRepository.findByCelular(cliente.getCelular());
        if (existente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existente.get());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
    }

    // 3. READ ONE: Obtener un cliente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. READ BY CELULAR: Obtener cliente por celular (Ideal para n8n)
    @GetMapping("/celular/{celular}")
    public ResponseEntity<Cliente> obtenerClientePorCelular(@PathVariable String celular) {
        Optional<Cliente> cliente = clienteRepository.findByCelular(celular);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            // Si el cliente no existe, retornamos un objeto por defecto con botActive: 1
            Cliente clienteDefault = new Cliente();
            clienteDefault.setCelular(celular);
            clienteDefault.setBotActive(1);
            return ResponseEntity.ok(clienteDefault);
        }
    }

    // 5. UPDATE: Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente detallesCliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        
        if (clienteExistente.isPresent()) {
            Cliente clienteAActualizar = clienteExistente.get();
            clienteAActualizar.setCelular(detallesCliente.getCelular());
            clienteAActualizar.setNombres(detallesCliente.getNombres());
            clienteAActualizar.setDireccionPredeterminada(detallesCliente.getDireccionPredeterminada());
            clienteAActualizar.setEstado(detallesCliente.getEstado());
            clienteAActualizar.setBotActive(detallesCliente.getBotActive());
            clienteAActualizar.setWhatsappId(detallesCliente.getWhatsappId());
            
            return ResponseEntity.ok(clienteRepository.save(clienteAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. DELETE: Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 7. UPDATE: Desactivar el bot para un cliente específico (Usado por n8n)
    @PutMapping("/whatsapp/{whatsappId}/desactivar-bot")
    public ResponseEntity<String> desactivarBot(@PathVariable String whatsappId) {
        
        // 1. Buscamos al cliente por su ID de WhatsApp
        Optional<Cliente> clienteOpt = clienteRepository.findByWhatsappId(whatsappId);
        
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            
            // 2. Cambiamos el estado a 0 (apagado)
            cliente.setBotActive(0); 
            
            // 3. Guardamos en la base de datos Oracle
            clienteRepository.save(cliente); 
            
            return ResponseEntity.ok("{\"status\": \"success\", \"message\": \"Bot desactivado exitosamente\"}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 8. UPDATE: Activar el bot para un cliente específico (Usado por n8n)
    @PutMapping("/whatsapp/{whatsappId}/activar-bot")
    public ResponseEntity<String> activarBot(@PathVariable String whatsappId) {
        Optional<Cliente> clienteOpt = clienteRepository.findByWhatsappId(whatsappId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setBotActive(1); // Volvemos a encender el bot
            clienteRepository.save(cliente);
            return ResponseEntity.ok("{\"status\": \"success\", \"message\": \"Bot reactivado\"}");
        }
        return ResponseEntity.notFound().build();
    }

    // 9. READ BY WHATSAPP ID: Obtener cliente por ID de WhatsApp
    @GetMapping("/whatsapp/{whatsappId}")
    public ResponseEntity<Cliente> obtenerClientePorWhatsappId(@PathVariable String whatsappId) {
        Optional<Cliente> cliente = clienteRepository.findByWhatsappId(whatsappId);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}