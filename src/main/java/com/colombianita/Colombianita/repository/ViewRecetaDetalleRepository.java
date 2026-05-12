package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ViewRecetaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewRecetaDetalleRepository extends JpaRepository<ViewRecetaDetalle, Long> {

    List<ViewRecetaDetalle> findByIdPresentacion(Long idPresentacion);
}
