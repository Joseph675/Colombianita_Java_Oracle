package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.PostulacionInnovaUcc;
import com.colombianita.Colombianita.repository.PostulacionInnovaUccRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/postulaciones")
@CrossOrigin(origins = "*")
public class PostulacionInnovaUccController {

    @Autowired
    private PostulacionInnovaUccRepository postulacionRepository;

    // 1. OBTENER POSTULACIONES POR CONVOCATORIA (Vista Jurado)
    @GetMapping("/convocatoria/{convocatoriaId}")
    public List<PostulacionInnovaUcc> listarPorConvocatoria(@PathVariable Long convocatoriaId) {
        return postulacionRepository.findByConvocatoriaId(convocatoriaId);
    }

    // 2. OBTENER POSTULACIONES POR PROYECTO (Vista Estudiante)
    @GetMapping("/proyecto/{proyectoId}")
    public List<PostulacionInnovaUcc> listarPorProyecto(@PathVariable Long proyectoId) {
        return postulacionRepository.findByProyectoId(proyectoId);
    }

    // 3. POSTULAR UN PROYECTO (Vista Estudiante)
    @PostMapping
    public ResponseEntity<?> aplicarAConvocatoria(@RequestBody PostulacionInnovaUcc postulacion) {
        Long idConvocatoria = postulacion.getConvocatoria().getId();
        Long idProyecto = postulacion.getProyecto().getId();

        // Evitar que postulen el mismo proyecto dos veces a la misma convocatoria
        if (postulacionRepository.existsByConvocatoriaIdAndProyectoId(idConvocatoria, idProyecto)) {
            return ResponseEntity.badRequest().body("Error: Este proyecto ya se encuentra postulado a esta convocatoria.");
        }

        return ResponseEntity.ok(postulacionRepository.save(postulacion));
    }

    // 4. CALIFICAR UNA POSTULACIÓN (Vista Jurado/Admin)
    // Permite cambiar el estado (APROBADA/RECHAZADA) y dejar una nota/feedback
    @PutMapping("/{id}/calificar")
    public ResponseEntity<PostulacionInnovaUcc> calificarPostulacion(
            @PathVariable Long id, 
            @RequestParam String estado, 
            @RequestParam(required = false) String nota) {
        
        Optional<PostulacionInnovaUcc> postulacionExistente = postulacionRepository.findById(id);
        
        if (postulacionExistente.isPresent()) {
            PostulacionInnovaUcc postulacion = postulacionExistente.get();
            postulacion.setEstado(estado.toUpperCase());
            if (nota != null) {
                postulacion.setNota(nota);
            }
            return ResponseEntity.ok(postulacionRepository.save(postulacion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. RETIRAR POSTULACIÓN (Vista Estudiante)
    // Usamos borrado físico. Si el estudiante se arrepiente, simplemente borramos el registro.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> retirarPostulacion(@PathVariable Long id) {
        if (postulacionRepository.existsById(id)) {
            postulacionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}