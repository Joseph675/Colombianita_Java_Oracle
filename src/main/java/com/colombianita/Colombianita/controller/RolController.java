package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Rol;
import com.colombianita.Colombianita.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    @PostMapping
    public Rol crearRol(@RequestBody Rol rol) {
        return rolRepository.save(rol);
    }
}