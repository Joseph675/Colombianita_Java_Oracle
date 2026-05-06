package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ViewAlertaInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViewAlertaInventarioRepository extends JpaRepository<ViewAlertaInventario, Long> {
    List<ViewAlertaInventario> findByIdSucursal(Long idSucursal);
}