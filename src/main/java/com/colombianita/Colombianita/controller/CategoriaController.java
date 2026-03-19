package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Categoria;
import com.colombianita.Colombianita.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // 1. READ: Obtener todas las categorías (GET)
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // 2. CREATE: Guardar una nueva categoría (POST)
    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // 3. READ ONE: Buscar una categoría específica por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. UPDATE: Modificar una categoría existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria detallesCategoria) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(id);
        
        if (categoriaExistente.isPresent()) {
            Categoria categoriaAActualizar = categoriaExistente.get();
            categoriaAActualizar.setNombre(detallesCategoria.getNombre());
            
            return ResponseEntity.ok(categoriaRepository.save(categoriaAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar una categoría (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}