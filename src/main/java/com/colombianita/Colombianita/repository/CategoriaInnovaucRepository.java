package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.CategoriaInnovaucc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaInnovaucRepository extends JpaRepository<CategoriaInnovaucc, Long> {
}
