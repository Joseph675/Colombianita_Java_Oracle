package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Factura;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class FacturaSpecification {

    public static Specification<Factura> metodoPagoEquals(String metodoPago) {
        return (root, query, builder) -> {
            if (metodoPago == null || metodoPago.isEmpty()) {
                return null;
            }
            return builder.equal(root.get("metodoPago"), metodoPago);
        };
    }

    public static Specification<Factura> fechaEmisionBetween(LocalDateTime inicio, LocalDateTime fin) {
        return (root, query, builder) -> {
            if (inicio == null || fin == null) {
                return null;
            }
            return builder.between(root.get("fechaEmision"), inicio, fin);
        };
    }
    // ¡Puedes agregar más filtros aquí! (Ej: idSucursal, usuario responsable, etc.)
}