package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.ProyectoMiembroInnovaUcc;
import com.colombianita.Colombianita.repository.ProyectoMiembroInnovaUccRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyecto-miembros")
@CrossOrigin(origins = "*")
public class ProyectoMiembroInnovaUccController {

    @Autowired
    private ProyectoMiembroInnovaUccRepository miembroRepository;

    // 1. OBTENER EL EQUIPO DE UN PROYECTO
    @GetMapping("/proyecto/{proyectoId}")
    public List<ProyectoMiembroInnovaUcc> listarEquipoDelProyecto(@PathVariable Long proyectoId) {
        return miembroRepository.findByProyectoId(proyectoId);
    }

    // 2. OBTENER LOS PROYECTOS DE UN ESTUDIANTE/USUARIO
    @GetMapping("/usuario/{usuarioId}")
    public List<ProyectoMiembroInnovaUcc> listarProyectosDelUsuario(@PathVariable Long usuarioId) {
        return miembroRepository.findByUsuarioId(usuarioId);
    }

    // 3. AGREGAR UN NUEVO MIEMBRO AL PROYECTO
    @PostMapping
    public ResponseEntity<?> agregarMiembro(@RequestBody ProyectoMiembroInnovaUcc miembro) {
        Long idProyecto = miembro.getProyecto().getId();
        Long idUsuario = miembro.getUsuario().getId();

        // Verificamos la regla UNIQUE antes de tocar la base de datos
        if (miembroRepository.existsByProyectoIdAndUsuarioId(idProyecto, idUsuario)) {
            return ResponseEntity.badRequest().body("Error: El usuario ya es miembro de este proyecto.");
        }

        ProyectoMiembroInnovaUcc nuevoMiembro = miembroRepository.save(miembro);
        return ResponseEntity.ok(nuevoMiembro);
    }

    // 4. ACTUALIZAR EL ROL DE UN MIEMBRO (Ej: pasarlo de 'COLABORADOR' a 'LIDER')
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoMiembroInnovaUcc> actualizarRol(@PathVariable Long id, @RequestBody ProyectoMiembroInnovaUcc detalles) {
        Optional<ProyectoMiembroInnovaUcc> miembroExistente = miembroRepository.findById(id);
        
        if (miembroExistente.isPresent()) {
            ProyectoMiembroInnovaUcc miembro = miembroExistente.get();
            miembro.setRolEnProyecto(detalles.getRolEnProyecto());
            return ResponseEntity.ok(miembroRepository.save(miembro));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. EXPULSAR/ELIMINAR A UN MIEMBRO DEL PROYECTO (Borrado Físico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long id) {
        if (miembroRepository.existsById(id)) {
            miembroRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}