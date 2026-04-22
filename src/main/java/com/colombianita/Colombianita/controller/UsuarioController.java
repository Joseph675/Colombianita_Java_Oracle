package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Usuario;
import com.colombianita.Colombianita.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. READ ALL: Obtener todos los usuarios (GET)
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // 2. CREATE: Guardar un nuevo usuario (POST)
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        // Nota: En un sistema real, aquí deberías encriptar (hash) el password antes de guardarlo.
        return usuarioRepository.save(usuario);
    }

    // 3. READ ONE: Buscar un usuario por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Actualizar datos del usuario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario detallesUsuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombres(detallesUsuario.getNombres());
            usuario.setEmail(detallesUsuario.getEmail());
            usuario.setPasswordHash(detallesUsuario.getPasswordHash());
            usuario.setEstado(detallesUsuario.getEstado());
            usuario.setRol(detallesUsuario.getRol());
            usuario.setSucursal(detallesUsuario.getSucursal());
            
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar un usuario (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
