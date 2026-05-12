package com.colombianita.Colombianita.controller;

import com.colombianita.Colombianita.entity.BufferMensaje;
import com.colombianita.Colombianita.repository.BufferMensajesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bot")
public class BotLockController {

    @Autowired
    private BufferMensajesRepository repository;

    @PostMapping("/check-lock")
    public ResponseEntity<Map<String, Object>> checkLock(@RequestBody Map<String, String> payload) {
        String celular = payload.get("numero");
        Map<String, Object> response = new HashMap<>();

        // 1. Buscamos si el celular ya existe en la BD
        Optional<BufferMensaje> bufferOpt = repository.findById(celular);

        if (bufferOpt.isPresent()) {
            BufferMensaje buffer = bufferOpt.get();
            long diferencia = System.currentTimeMillis() - buffer.getUltimaActualizacion().getTime();
            
            // 2. Si el mensaje llegó hace menos de 5 segundos, bloqueamos
            if (diferencia < 5000) {
                response.put("proceed", false);
                return ResponseEntity.ok(response);
            }
            
            // 3. Si ya pasó el tiempo, actualizamos la hora del registro
            buffer.setUltimaActualizacion(new Timestamp(System.currentTimeMillis()));
            repository.save(buffer);
        } else {
            // 4. Si no existe, creamos el nuevo registro
            BufferMensaje nuevoRegistro = new BufferMensaje();
            nuevoRegistro.setCelular(celular);
            nuevoRegistro.setUltimaActualizacion(new Timestamp(System.currentTimeMillis()));
            repository.save(nuevoRegistro);
        }

        response.put("proceed", true);
        return ResponseEntity.ok(response);
    }

    // Endpoint 1: Acumular mensaje cuando proceed=false
    @PostMapping("/append-message")
    public ResponseEntity<Map<String, Object>> appendMessage(
            @RequestBody Map<String, String> payload) {
        
        String celular = payload.get("numero");
        String nuevoMensaje = payload.get("mensaje");
        Map<String, Object> response = new HashMap<>();
        
        Optional<BufferMensaje> bufferOpt = repository.findById(celular);
        
        if (bufferOpt.isPresent()) {
            BufferMensaje buffer = bufferOpt.get();
            
            // Concatenar con salto de línea
            String acumulado = buffer.getMensajeAcumulado();
            if (acumulado == null || acumulado.isEmpty()) {
                buffer.setMensajeAcumulado(nuevoMensaje);
            } else {
                buffer.setMensajeAcumulado(acumulado + "\n" + nuevoMensaje);
            }
            
            // Actualizar timestamp para reiniciar el contador de 5 segundos
            buffer.setUltimaActualizacion(
                new Timestamp(System.currentTimeMillis()));
            repository.save(buffer);
            
            response.put("ok", true);
        } else {
            response.put("ok", false);
            response.put("error", "Registro no encontrado");
        }
        
        return ResponseEntity.ok(response);
    }

    // Endpoint 2: Recuperar acumulado y limpiar
    @PostMapping("/get-and-clear")
    public ResponseEntity<Map<String, Object>> getAndClear(
            @RequestBody Map<String, String> payload) {
        
        String celular = payload.get("numero");
        Map<String, Object> response = new HashMap<>();
        
        Optional<BufferMensaje> bufferOpt = repository.findById(celular);
        
        if (bufferOpt.isPresent()) {
            BufferMensaje buffer = bufferOpt.get();
            
            String acumulado = buffer.getMensajeAcumulado();
            
            // Limpiar el acumulado
            buffer.setMensajeAcumulado(null);
            repository.save(buffer);
            
            response.put("mensajes", acumulado != null ? acumulado : "");
            response.put("ok", true);
        } else {
            response.put("mensajes", "");
            response.put("ok", false);
        }
        
        return ResponseEntity.ok(response);
    }
}