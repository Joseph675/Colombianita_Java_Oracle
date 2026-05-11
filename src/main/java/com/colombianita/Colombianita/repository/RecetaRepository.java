package com.colombianita.Colombianita.repository;
import com.colombianita.Colombianita.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    
    // Obtener todos los ingredientes de una presentación específica
    List<Receta> findByPresentacionIdPresentacion(Long idPresentacion);
}