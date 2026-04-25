package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    // Método útil para buscar todos los productos de un pedido específico
    // List<DetallePedido> findByPedidoIdPedido(Long idPedido);
}