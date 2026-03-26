package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Sucursal;
import com.colombianita.Colombianita.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@CrossOrigin(origins = "*")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;

    @GetMapping
    public List<Sucursal> listarSucursales() {
        return sucursalRepository.findAll();
    }

    @PostMapping
    public Sucursal crearSucursal(@RequestBody Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }
}