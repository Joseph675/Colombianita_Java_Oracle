package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Ingrediente;
import com.colombianita.Colombianita.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
@CrossOrigin(origins = "*")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }

    @PostMapping
    public Ingrediente crearIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }
}