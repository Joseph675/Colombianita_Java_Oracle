package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ViewCajaTurnoActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViewCajaTurnoActivoRepository extends JpaRepository<ViewCajaTurnoActivo, Long> {
    List<ViewCajaTurnoActivo> findByIdSucursal(Long idSucursal);
}