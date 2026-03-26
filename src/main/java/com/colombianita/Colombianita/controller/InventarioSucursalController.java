package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.InventarioSucursal;
import com.colombianita.Colombianita.repository.InventarioSucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "*")
public class InventarioSucursalController {

    @Autowired
    private InventarioSucursalRepository inventarioRepository;

    @GetMapping
    public List<InventarioSucursal> listarInventario() {
        return inventarioRepository.findAll();
    }

    @PostMapping
    public InventarioSucursal agregarInventario(@RequestBody InventarioSucursal inventario) {
        return inventarioRepository.save(inventario);
    }
}