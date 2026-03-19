package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Producto;
import com.colombianita.Colombianita.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // 1. READ: Obtener todo el catálogo (GET)
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // 2. CREATE: Guardar un nuevo producto (POST)
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        // @RequestBody toma el JSON que envíe Angular y lo convierte en un objeto Java
        return productoRepository.save(producto);
    }

    // 3. READ ONE: Buscar un producto específico por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get()); // Retorna código 200 OK con el producto
        } else {
            return ResponseEntity.notFound().build(); // Retorna código 404 No Encontrado
        }
    }

    // 4. UPDATE: Modificar un producto existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto) {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        
        if (productoExistente.isPresent()) {
            Producto productoAActualizar = productoExistente.get();
            // Actualizamos los campos
            productoAActualizar.setCategoria(detallesProducto.getCategoria());
            productoAActualizar.setNombre(detallesProducto.getNombre());
            productoAActualizar.setDescripcion(detallesProducto.getDescripcion());
            productoAActualizar.setImagenUrl(detallesProducto.getImagenUrl());
            
            // Guardamos los cambios en la base de datos
            return ResponseEntity.ok(productoRepository.save(productoAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE: Eliminar un producto del menú (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}