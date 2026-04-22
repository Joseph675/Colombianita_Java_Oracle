package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.UsuarioDTO;
import com.colombianita.Colombianita.entity.Usuario;
import com.colombianita.Colombianita.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método auxiliar para convertir Entidad a DTO
    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombres(usuario.getNombres());
        dto.setEmail(usuario.getEmail());
        dto.setEstado(usuario.getEstado());
        dto.setRol(usuario.getRol());
        dto.setSucursal(usuario.getSucursal());
        return dto;
    }

    // 1. READ ALL: Obtener todos los usuarios (GET)
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // 2. CREATE: Guardar un nuevo usuario (POST)
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody Usuario usuario) {
        // Nota: En un sistema real, aquí deberías encriptar (hash) el password antes de guardarlo.
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(convertirADTO(usuarioGuardado));
    }

    // 3. READ ONE: Buscar un usuario por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(convertirADTO(usuario.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Actualizar datos del usuario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario detallesUsuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombres(detallesUsuario.getNombres());
            usuario.setEmail(detallesUsuario.getEmail());
            
            // Solo actualizamos el password si se provee uno nuevo
            if (detallesUsuario.getPasswordHash() != null && !detallesUsuario.getPasswordHash().isEmpty()) {
                usuario.setPasswordHash(detallesUsuario.getPasswordHash());
            }
            
            usuario.setEstado(detallesUsuario.getEstado());
            usuario.setRol(detallesUsuario.getRol());
            usuario.setSucursal(detallesUsuario.getSucursal());
            
            Usuario usuarioActualizado = usuarioRepository.save(usuario);
            return ResponseEntity.ok(convertirADTO(usuarioActualizado));
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
