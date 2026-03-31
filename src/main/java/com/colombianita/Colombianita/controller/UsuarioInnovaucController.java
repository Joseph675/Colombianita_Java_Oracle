package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.UsuarioInnovaucc;
import com.colombianita.Colombianita.repository.UsuarioInnovaucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios-innovaucc")
@CrossOrigin(origins = "*")
public class UsuarioInnovaucController {

    @Autowired
    private UsuarioInnovaucRepository usuarioInnovaucRepository;

    // 1. READ: Obtener todos los usuarios (GET)
    @GetMapping
    public List<UsuarioInnovaucc> listarUsuarios() {
        return usuarioInnovaucRepository.findAll();
    }

    // 2. CREATE: Guardar un nuevo usuario (POST)
    @PostMapping
    public UsuarioInnovaucc crearUsuario(@RequestBody UsuarioInnovaucc usuario) {
        return usuarioInnovaucRepository.save(usuario);
    }

    // 3. READ ONE: Buscar un usuario específico por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioInnovaucc> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<UsuarioInnovaucc> usuario = usuarioInnovaucRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3b. READ ONE: Buscar un usuario específico por su email (GET)
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioInnovaucc> obtenerUsuarioPorEmail(@PathVariable String email) {
        Optional<UsuarioInnovaucc> usuario = usuarioInnovaucRepository.findByEmail(email);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar un usuario existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioInnovaucc> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioInnovaucc detallesUsuario) {
        Optional<UsuarioInnovaucc> usuarioExistente = usuarioInnovaucRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            UsuarioInnovaucc usuarioAActualizar = usuarioExistente.get();
            usuarioAActualizar.setNombre(detallesUsuario.getNombre());
            usuarioAActualizar.setApellido(detallesUsuario.getApellido());
            usuarioAActualizar.setEmail(detallesUsuario.getEmail());
            usuarioAActualizar.setPasswordHash(detallesUsuario.getPasswordHash());
            usuarioAActualizar.setRol(detallesUsuario.getRol());
            usuarioAActualizar.setPrograma(detallesUsuario.getPrograma());
            usuarioAActualizar.setSede(detallesUsuario.getSede());
            usuarioAActualizar.setBio(detallesUsuario.getBio());
            usuarioAActualizar.setFotoUrl(detallesUsuario.getFotoUrl());
            usuarioAActualizar.setIniciales(detallesUsuario.getIniciales());
            usuarioAActualizar.setActivo(detallesUsuario.getActivo());

            return ResponseEntity.ok(usuarioInnovaucRepository.save(usuarioAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar un usuario (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioInnovaucRepository.existsById(id)) {
            usuarioInnovaucRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
