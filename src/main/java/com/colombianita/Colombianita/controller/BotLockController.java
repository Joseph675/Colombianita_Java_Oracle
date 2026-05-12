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

    // 1. Endpoint Unificado: Agrupa el mensaje y decide el flujo
    @PostMapping("/buffer-message")
    public ResponseEntity<Map<String, Object>> bufferMessage(@RequestBody Map<String, String> payload) {
        String celular = payload.get("numero");
        String nuevoMensaje = payload.get("mensaje");
        Map<String, Object> response = new HashMap<>();

        Optional<BufferMensaje> bufferOpt = repository.findById(celular);

        if (bufferOpt.isPresent()) {
            BufferMensaje buffer = bufferOpt.get();
            long diferencia = System.currentTimeMillis() - buffer.getUltimaActualizacion().getTime();

            // Concatenamos el nuevo mensaje al que ya existía
            String acumulado = buffer.getMensajeAcumulado();
            buffer.setMensajeAcumulado((acumulado == null || acumulado.isEmpty()) ? 
                            nuevoMensaje : acumulado + ". " + nuevoMensaje);
            buffer.setUltimaActualizacion(new Timestamp(System.currentTimeMillis()));
            repository.save(buffer);

            // Si llegó hace menos de 5 segundos, agrupamos y DETENEMOS el flujo en n8n
            if (diferencia < 5000) {
                response.put("proceed", false);
            } else {
                // Si pasó mucho tiempo, es una nueva conversación. n8n DEBE continuar.
                response.put("proceed", true);
            }
        } else {
            // Es la primera vez que este cliente escribe hoy
            BufferMensaje nuevoRegistro = new BufferMensaje();
            nuevoRegistro.setCelular(celular);
            nuevoRegistro.setMensajeAcumulado(nuevoMensaje); // ¡AQUÍ GUARDAMOS EL PRIMER MENSAJE!
            nuevoRegistro.setUltimaActualizacion(new Timestamp(System.currentTimeMillis()));
            repository.save(nuevoRegistro);

            response.put("proceed", true); // n8n inicia la espera de 5 segundos
        }

        return ResponseEntity.ok(response);
    }

    // 2. Endpoint para Recuperar y Limpiar (Este se llama DESPUÉS del nodo Wait en n8n)
    @PostMapping("/get-and-clear")
    public ResponseEntity<Map<String, Object>> getAndClear(@RequestBody Map<String, String> payload) {
        String celular = payload.get("numero");
        Map<String, Object> response = new HashMap<>();

        Optional<BufferMensaje> bufferOpt = repository.findById(celular);

        if (bufferOpt.isPresent()) {
            BufferMensaje buffer = bufferOpt.get();
            String acumulado = buffer.getMensajeAcumulado();

            // Limpiamos el acumulado para la próxima interacción
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