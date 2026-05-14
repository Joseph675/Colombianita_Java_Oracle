package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.Combo;
import com.colombianita.Colombianita.entity.DetalleCombo;
import com.colombianita.Colombianita.entity.PresentacionProducto;
import com.colombianita.Colombianita.dto.ComboRequestDto;
import com.colombianita.Colombianita.dto.ItemComboDto;
import com.colombianita.Colombianita.dto.ComboMasivoRequestDto;
import com.colombianita.Colombianita.repository.ComboRepository;
import com.colombianita.Colombianita.repository.DetalleComboRepository;
import com.colombianita.Colombianita.repository.PresentacionProductoRepository;
import com.colombianita.Colombianita.service.ComboSpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private ComboSpService comboSpService; // <-- Inyectamos el servicio SP

    @GetMapping
    public List<Combo> listarCombos() {
        return comboRepository.findAll();
    }

    @PostMapping
    public Combo crearCombo(@RequestBody Combo combo) {
        return comboRepository.save(combo);
    }

    // Endpoint para guardar Combo + Detalles de un solo golpe con PL/SQL (Stored Procedure)
    @PostMapping("/crear-completo")
    public ResponseEntity<?> crearComboCompleto(@RequestBody ComboRequestDto requestDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Llamamos a Oracle y nos devuelve el ID generado
            Long idComboGenerado = comboSpService.crearComboCompletoSp(requestDTO);
            
            response.put("mensaje", "Combo creado con éxito mediante Procedimiento Almacenado");
            response.put("idCombo", idComboGenerado);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error interno al crear combo en base de datos: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    // ENDPOINT RECOMENDADO: Generador Automático de Combos (El Ahorro de tiempo real)
    @PostMapping("/generar-masivo")
    public ResponseEntity<?> generarCombosMasivos(@RequestBody ComboMasivoRequestDto request) {
        List<Long> combosCreados = new ArrayList<>();
        
        try {
            // Iteramos sobre todos los IDs variables (Las Pizzas)
            for (Long idVariable : request.getIdPresentacionesVariables()) {
                Optional<PresentacionProducto> presentacionOpt = presentacionRepository.findById(idVariable);
                
                if (presentacionOpt.isPresent()) {
                    PresentacionProducto productoVariable = presentacionOpt.get();
                    
                    ComboRequestDto dtoIndividual = new ComboRequestDto();
                    // Sustituimos el comodín {sabor} por el nombre real de la pizza
                    String nombreFinal = request.getNombreBase().replace("{sabor}", productoVariable.getProducto().getNombre());
                    dtoIndividual.setNombre(nombreFinal);
                    dtoIndividual.setDescripcion(request.getDescripcion());
                    dtoIndividual.setPrecioFijo(request.getPrecioFijo());
                    dtoIndividual.setFechaInicio(request.getFechaInicio());
                    dtoIndividual.setFechaFin(request.getFechaFin());
                    dtoIndividual.setDiasAplica(request.getDiasAplica());
                    
                    List<ItemComboDto> items = new ArrayList<>();
                    // Metemos la Pizza (Variable)
                    items.add(new ItemComboDto(idVariable, 1));
                    
                    // Metemos la Gaseosa (Los fijos)
                    if (request.getItemsFijos() != null) items.addAll(request.getItemsFijos());
                    
                    dtoIndividual.setItems(items);
                    
                    // Llamamos a tu Procedimiento Almacenado para que lo guarde volando
                    combosCreados.add(comboSpService.crearComboCompletoSp(dtoIndividual));
                }
            }
            return ResponseEntity.ok(Map.of("mensaje", "Se generaron " + combosCreados.size() + " combos exitosamente.", "ids", combosCreados));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Fallo al generar combos masivos: " + e.getMessage()));
        }
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