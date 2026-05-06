package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ViewEstadoMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViewEstadoMesaRepository extends JpaRepository<ViewEstadoMesa, Long> {
    List<ViewEstadoMesa> findByIdSucursal(Long idSucursal);
}