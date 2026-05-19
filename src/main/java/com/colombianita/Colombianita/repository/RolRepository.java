package com.colombianita.Colombianita.repository;
import com.colombianita.Colombianita.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// PATRÓN: Repository — abstrae el acceso a datos de la entidad Rol.
// PATRÓN: Template Method — JpaRepository provee findAll, findById, save, delete, etc.
//   No se necesitan métodos adicionales porque Rol es un catálogo simple de solo lectura.
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {}