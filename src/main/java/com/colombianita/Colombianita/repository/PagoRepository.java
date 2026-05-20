package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByPedidoIdPedido(Long idPedido);

    List<Pago> findByFacturaIdFactura(Long idFactura);

    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pago p " +
           "WHERE p.factura IS NOT NULL " +
           "AND p.factura.cierreCaja.idCierre = :idCierre " +
           "AND UPPER(p.metodoPago) = UPPER(:metodoPago)")
    BigDecimal sumarMontoByMetodo(@Param("idCierre") Long idCierre, @Param("metodoPago") String metodoPago);
}
