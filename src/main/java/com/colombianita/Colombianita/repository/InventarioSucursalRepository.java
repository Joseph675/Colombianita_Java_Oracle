package com.colombianita.Colombianita.repository;
import com.colombianita.Colombianita.entity.InventarioSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioSucursalRepository extends JpaRepository<InventarioSucursal, Long> {}
