package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.SedeInnovaucc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeInnovaucRepository extends JpaRepository<SedeInnovaucc, Long> {
}
