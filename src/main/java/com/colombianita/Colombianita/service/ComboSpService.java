package com.colombianita.Colombianita.service;

import com.colombianita.Colombianita.dto.ComboRequestDto;
import com.colombianita.Colombianita.dto.ItemComboDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Struct;
import java.sql.Types;

// PATRÓN: Strategy — encapsula la estrategia de creación de combos mediante un Stored Procedure
//   de Oracle. Si en el futuro se cambia la estrategia (ej: lógica Java pura), solo se reemplaza
//   esta clase sin tocar el controller.
// PATRÓN: Adapter — convierte objetos Java (List<ItemComboDto>) al formato nativo de Oracle
//   (STRUCT / ARRAY de tipos definidos en la BD: OBJ_ITEM_COMBO / TABLA_ITEM_COMBO).
@Service
public class ComboSpService {

    @Autowired
    private DataSource dataSource;

    public Long crearComboCompletoSp(ComboRequestDto request) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            
            // PATRÓN: Adapter — aquí ocurre la conversión: List<ItemComboDto> (mundo Java)
            //   → Struct[] → Array (tipos propietarios de Oracle)
            Struct[] structArray = new Struct[request.getItems().size()];
            int i = 0;
            
            for (ItemComboDto item : request.getItems()) {
                Object[] attributes = new Object[] { 
                    item.getIdPresentacion(), 
                    item.getCantidad() 
                };
                structArray[i++] = conn.createStruct("OBJ_ITEM_COMBO", attributes);
            }

            // 2. Crear el Array de Oracle a partir de los Structs
            Array itemsArray = conn.createArrayOf("TABLA_ITEM_COMBO", structArray);

            // 3. Preparar la llamada al Stored Procedure
            String sql = "{ call SP_CREAR_COMBO_COMPLETO(?, ?, ?, ?, ?, ?, ?, ?) }";
            
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setString(1, request.getNombre());
                stmt.setString(2, request.getDescripcion());
                stmt.setBigDecimal(3, request.getPrecioFijo()); // Ajustado a BigDecimal
                
                if (request.getFechaInicio() != null && !request.getFechaInicio().toString().isEmpty()) {
                    stmt.setDate(4, java.sql.Date.valueOf(request.getFechaInicio().toLocalDate()));
                } else { stmt.setNull(4, Types.DATE); }
                
                if (request.getFechaFin() != null && !request.getFechaFin().toString().isEmpty()) {
                    stmt.setDate(5, java.sql.Date.valueOf(request.getFechaFin().toLocalDate()));
                } else { stmt.setNull(5, Types.DATE); }
                
                stmt.setString(6, request.getDiasAplica());
                stmt.setArray(7, itemsArray);
                stmt.registerOutParameter(8, Types.NUMERIC);

                stmt.execute();
                return stmt.getLong(8);
            }
        }
    }
}