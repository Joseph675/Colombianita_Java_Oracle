package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Factura;
import com.colombianita.Colombianita.entity.Pago;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class FacturaSpecification {

    // Filtra facturas que tengan al menos un pago con el método indicado.
    // Se usa EXISTS sobre pagos porque factura.metodoPago ahora puede ser null o 'MIXTO'.
    public static Specification<Factura> metodoPagoEquals(String metodoPago) {
        return (root, query, builder) -> {
            if (metodoPago == null || metodoPago.isEmpty()) {
                return null;
            }
            Subquery<Long> sub = query.subquery(Long.class);
            Root<Pago> pago = sub.from(Pago.class);
            sub.select(builder.literal(1L))
               .where(
                   builder.equal(pago.get("factura"), root),
                   builder.equal(
                       builder.upper(pago.get("metodoPago")),
                       metodoPago.toUpperCase()
                   )
               );
            return builder.exists(sub);
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