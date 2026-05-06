package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.DetalleCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleComboRepository extends JpaRepository<DetalleCombo, Long> {
    List<DetalleCombo> findByComboIdCombo(Long idCombo);
}