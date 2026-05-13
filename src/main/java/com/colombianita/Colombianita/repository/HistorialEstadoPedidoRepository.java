package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.HistorialEstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialEstadoPedidoRepository extends JpaRepository<HistorialEstadoPedido, Long> {
    
    // Obtener la traza de estados de un pedido ordenado por fecha descendente
    List<HistorialEstadoPedido> findByPedidoIdPedidoOrderByFechaCambioDesc(Long idPedido);
}