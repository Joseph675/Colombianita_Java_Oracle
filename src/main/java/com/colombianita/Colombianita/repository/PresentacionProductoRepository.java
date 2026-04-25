package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.PresentacionProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface PresentacionProductoRepository extends JpaRepository<PresentacionProducto, Long> {

    // Método mágico de Spring: "Borra las presentaciones cuyo Producto tenga este ID"
    @Transactional
    void deleteByProductoIdProducto(Long idProducto);

    // Método clave para WhatsApp: Solo mostrar productos activos (estado = 1)
    List<PresentacionProducto> findByEstado(Integer estado);
}