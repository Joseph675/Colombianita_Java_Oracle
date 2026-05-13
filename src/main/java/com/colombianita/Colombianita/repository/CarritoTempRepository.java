package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.CarritoTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoTempRepository extends JpaRepository<CarritoTemp, String> {
    // JpaRepository ya incluye por defecto los métodos save(), findById(), deleteById(), etc.
}