package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    // Método útil para buscar todas las mesas de una sucursal en específico
    // List<Mesa> findBySucursalIdSucursal(Long idSucursal);

    // Validar que no se repita el número de mesa en una misma sucursal
    boolean existsByNumeroMesaAndSucursalIdSucursal(Integer numeroMesa, Long idSucursal);
}