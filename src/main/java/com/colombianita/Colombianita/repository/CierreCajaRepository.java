package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.CierreCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CierreCajaRepository extends JpaRepository<CierreCaja, Long> {
    @Query("SELECT c FROM CierreCaja c WHERE c.diferencia <= :limiteFaltante AND c.fechaApertura >= :fechaDesde")
    List<CierreCaja> findCierresConFaltanteCritico(@Param("limiteFaltante") BigDecimal limiteFaltante, @Param("fechaDesde") LocalDateTime fechaDesde);
}