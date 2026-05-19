package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// PATRÓN: Repository — abstrae el acceso a datos de la entidad Factura.
// PATRÓN: Specification — al extender JpaSpecificationExecutor, este repositorio acepta objetos
//   Specification (definidos en FacturaSpecification) para construir queries dinámicas en tiempo de ejecución.
// PATRÓN: Template Method — JpaRepository provee el esqueleto de findAll, save, deleteById, etc.
//   Solo sobreescribimos lo específico con el método findByPedidoIdPedido.
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>, JpaSpecificationExecutor<Factura> {
    Optional<Factura> findByPedidoIdPedido(Long idPedido);
}