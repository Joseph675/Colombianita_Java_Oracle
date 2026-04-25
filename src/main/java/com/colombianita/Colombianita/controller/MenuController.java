package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.dto.MenuBotDTO;
import com.colombianita.Colombianita.entity.PresentacionProducto;
import com.colombianita.Colombianita.repository.PresentacionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    @Autowired
    private PresentacionProductoRepository presentacionRepository;

    // EL ENDPOINT QUE CONSUMIRÁ n8n
    @GetMapping
    public ResponseEntity<List<PresentacionProducto>> obtenerMenuParaWhatsApp() {
        // Le pedimos a la BD solo las presentaciones activas
        List<PresentacionProducto> menuCompleto = presentacionRepository.findByEstado(1);
        
        if(menuCompleto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(menuCompleto);
    }

    // ENDPOINT SIMPLIFICADO PARA EL BOT DE WHATSAPP (n8n)
    @GetMapping("/bot")
    public ResponseEntity<List<MenuBotDTO>> obtenerMenuSimplificado() {
        List<PresentacionProducto> menuCompleto = presentacionRepository.findByEstado(1);
        
        if(menuCompleto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<MenuBotDTO> menuBot = menuCompleto.stream().map(p -> {
            MenuBotDTO dto = new MenuBotDTO();
            // Agrega esta línea nueva:
            dto.setIdPresentacion(p.getIdPresentacion()); 
            
            dto.setCategoria(p.getProducto().getCategoria().getNombre());
            dto.setProducto(p.getProducto().getNombre());
            dto.setPresentacion(p.getNombrePresentacion());
            dto.setPrecio(p.getPrecio());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(menuBot);
    }
}