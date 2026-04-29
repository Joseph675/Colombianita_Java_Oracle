package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Cliente;
import com.colombianita.Colombianita.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 2. CREATE: Guardar un nuevo cliente
    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
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
            return ResponseEntity.notFound().build();
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
}