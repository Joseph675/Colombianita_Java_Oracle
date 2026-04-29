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
}