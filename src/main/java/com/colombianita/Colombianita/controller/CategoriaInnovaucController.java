package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.CategoriaInnovaucc;
import com.colombianita.Colombianita.repository.CategoriaInnovaucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias-innovaucc")
@CrossOrigin(origins = "*")
public class CategoriaInnovaucController {

    @Autowired
    private CategoriaInnovaucRepository categoriaInnovaucRepository;

    // 1. READ: Obtener todas las categorías (GET)
    @GetMapping
    public List<CategoriaInnovaucc> listarCategorias() {
        return categoriaInnovaucRepository.findAll();
    }

    // 2. CREATE: Guardar una nueva categoría (POST)
    @PostMapping
    public CategoriaInnovaucc crearCategoria(@RequestBody CategoriaInnovaucc categoria) {
        return categoriaInnovaucRepository.save(categoria);
    }

    // 3. READ ONE: Buscar una categoría específica por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaInnovaucc> obtenerCategoriaPorId(@PathVariable Long id) {
        Optional<CategoriaInnovaucc> categoria = categoriaInnovaucRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar una categoría existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaInnovaucc> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaInnovaucc detallesCategoria) {
        Optional<CategoriaInnovaucc> categoriaExistente = categoriaInnovaucRepository.findById(id);

        if (categoriaExistente.isPresent()) {
            CategoriaInnovaucc categoriaAActualizar = categoriaExistente.get();
            categoriaAActualizar.setNombre(detallesCategoria.getNombre());
            categoriaAActualizar.setEmoji(detallesCategoria.getEmoji());
            categoriaAActualizar.setColorThumb(detallesCategoria.getColorThumb());
            categoriaAActualizar.setActiva(detallesCategoria.getActiva());

            return ResponseEntity.ok(categoriaInnovaucRepository.save(categoriaAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar una categoría (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        if (categoriaInnovaucRepository.existsById(id)) {
            categoriaInnovaucRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
