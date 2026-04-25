package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas después, por ejemplo:
    // List<Pedido> findBySucursalIdSucursal(Long idSucursal);
}