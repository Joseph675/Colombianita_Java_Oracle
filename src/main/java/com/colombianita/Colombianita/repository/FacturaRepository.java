package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>, JpaSpecificationExecutor<Factura> {
    Optional<Factura> findByPedidoIdPedido(Long idPedido);
}