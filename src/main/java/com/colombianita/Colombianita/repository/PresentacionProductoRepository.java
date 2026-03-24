package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.entity.PresentacionProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentacionProductoRepository extends JpaRepository<PresentacionProducto, Long> {}