package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ViewVentasDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ViewVentasDiaRepository extends JpaRepository<ViewVentasDia, Long> {
    List<ViewVentasDia> findByIdSucursalAndFecha(Long idSucursal, LocalDate fecha);
    List<ViewVentasDia> findByFecha(LocalDate fecha);
}