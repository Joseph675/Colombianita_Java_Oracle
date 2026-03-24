package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.PresentacionProducto;
import com.colombianita.Colombianita.repository.PresentacionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presentaciones")
@CrossOrigin(origins = "*")
public class PresentacionProductoController {

    @Autowired
    private PresentacionProductoRepository presentacionRepository;

    @GetMapping
    public List<PresentacionProducto> listarPresentaciones() {
        return presentacionRepository.findAll();
    }

    @PostMapping
    public PresentacionProducto crearPresentacion(@RequestBody PresentacionProducto presentacion) {
        return presentacionRepository.save(presentacion);
    }
}