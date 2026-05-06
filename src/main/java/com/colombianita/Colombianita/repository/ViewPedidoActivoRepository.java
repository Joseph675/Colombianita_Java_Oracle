package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ViewPedidoActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViewPedidoActivoRepository extends JpaRepository<ViewPedidoActivo, Long> {
    List<ViewPedidoActivo> findByIdSucursal(Long idSucursal);
}