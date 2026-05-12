package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.dto.CostoRecetaDTO;
import com.colombianita.Colombianita.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    @Query("SELECT r FROM Receta r JOIN FETCH r.ingrediente WHERE r.presentacion.idPresentacion = :idPresentacion")
    List<Receta> findConIngredienteByPresentacionId(@Param("idPresentacion") Long idPresentacion);

    @Query("SELECT new com.colombianita.Colombianita.dto.CostoRecetaDTO(" +
           "r.presentacion.idPresentacion, " +
           "SUM(r.cantidadNecesaria * i.costoUnitario), " +
           "COUNT(r)) " +
           "FROM Receta r JOIN r.ingrediente i " +
           "WHERE r.presentacion.idPresentacion = :idPresentacion " +
           "GROUP BY r.presentacion.idPresentacion")
    CostoRecetaDTO calcularCostoPorPresentacion(@Param("idPresentacion") Long idPresentacion);
}