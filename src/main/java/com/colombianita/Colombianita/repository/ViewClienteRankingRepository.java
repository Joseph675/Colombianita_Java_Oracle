package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.ViewClienteRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ViewClienteRankingRepository extends JpaRepository<ViewClienteRanking, Long> {
}