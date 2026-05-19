package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Factura;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

// PATRÓN: Specification — encapsula cada criterio de filtrado como un objeto reutilizable.
//   Las especificaciones se combinan con .and() / .or() en el controller sin tocar el repositorio.
//   Esto evita tener un método findBy* por cada combinación de filtros posible.
public class FacturaSpecification {

    // PATRÓN: Specification — criterio de filtro por método de pago. Retorna null si no aplica (filtro ignorado).
    public static Specification<Factura> metodoPagoEquals(String metodoPago) {
        return (root, query, builder) -> {
            if (metodoPago == null || metodoPago.isEmpty()) {
                return null;
            }
            return builder.equal(root.get("metodoPago"), metodoPago);
        };
    }

    // PATRÓN: Specification — criterio de filtro por rango de fechas. Retorna null si no aplica.
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