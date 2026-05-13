package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Combo;
import com.colombianita.Colombianita.entity.DetalleCombo;
import com.colombianita.Colombianita.entity.PresentacionProducto;
import com.colombianita.Colombianita.dto.ComboRequestDto;
import com.colombianita.Colombianita.dto.ItemComboDto;
import com.colombianita.Colombianita.repository.ComboRepository;
import com.colombianita.Colombianita.repository.DetalleComboRepository;
import com.colombianita.Colombianita.repository.PresentacionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/combos")
@CrossOrigin(origins = "*")
public class ComboController {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private DetalleComboRepository detalleComboRepository;

    @Autowired
    private PresentacionProductoRepository presentacionRepository;

    @GetMapping
    public List<Combo> listarCombos() {
        return comboRepository.findAll();
    }

    @PostMapping
    public Combo crearCombo(@RequestBody Combo combo) {
        return comboRepository.save(combo);
    }

    // Endpoint para guardar Combo + Detalles en una sola petición simulando tu SP
    @PostMapping("/crear-completo")
    @Transactional
    public ResponseEntity<?> crearComboCompleto(@RequestBody ComboRequestDto requestDTO) {
        
        // 1. Crear y guardar la cabecera del Combo
        Combo nuevoCombo = new Combo();
        nuevoCombo.setNombre(requestDTO.getNombre());
        nuevoCombo.setDescripcion(requestDTO.getDescripcion());
        nuevoCombo.setPrecioFijo(requestDTO.getPrecioFijo());
        nuevoCombo.setFechaInicio(requestDTO.getFechaInicio());
        nuevoCombo.setFechaFin(requestDTO.getFechaFin());
        nuevoCombo.setDiasAplica(requestDTO.getDiasAplica());
        nuevoCombo.setEstado(1); // Por defecto activo
        
        // Guardamos y Oracle automáticamente genera su ID
        Combo comboGuardado = comboRepository.save(nuevoCombo);

        // 2. Insertar los detalles asociados a este combo
        if (requestDTO.getItems() != null && !requestDTO.getItems().isEmpty()) {
            for (ItemComboDto item : requestDTO.getItems()) {
                Optional<PresentacionProducto> presentacionOpt = presentacionRepository.findById(item.getIdPresentacion());
                
                if (presentacionOpt.isPresent()) {
                    DetalleCombo detalle = new DetalleCombo();
                    detalle.setCombo(comboGuardado);
                    detalle.setPresentacion(presentacionOpt.get());
                    detalle.setCantidad(item.getCantidad());
                    detalleComboRepository.save(detalle);
                } else {
                    throw new RuntimeException("Error: La presentación con ID " + item.getIdPresentacion() + " no existe. Rollback aplicado.");
                }
            }
        }
        return ResponseEntity.ok(comboGuardado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Combo> obtenerComboPorId(@PathVariable Long id) {
        Optional<Combo> combo = comboRepository.findById(id);
        return combo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Combo> actualizarCombo(@PathVariable Long id, @RequestBody Combo detallesCombo) {
        Optional<Combo> comboExistente = comboRepository.findById(id);

        if (comboExistente.isPresent()) {
            Combo comboAActualizar = comboExistente.get();
            comboAActualizar.setNombre(detallesCombo.getNombre());
            comboAActualizar.setDescripcion(detallesCombo.getDescripcion());
            comboAActualizar.setPrecioFijo(detallesCombo.getPrecioFijo());
            comboAActualizar.setFechaInicio(detallesCombo.getFechaInicio());
            comboAActualizar.setFechaFin(detallesCombo.getFechaFin());
            comboAActualizar.setEstado(detallesCombo.getEstado());
            comboAActualizar.setDiasAplica(detallesCombo.getDiasAplica());

            return ResponseEntity.ok(comboRepository.save(comboAActualizar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCombo(@PathVariable Long id) {
        if (comboRepository.existsById(id)) {
            comboRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}