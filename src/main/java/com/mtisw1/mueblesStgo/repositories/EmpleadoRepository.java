package com.mtisw1.mueblesStgo.repositories;

import com.mtisw1.mueblesStgo.entities.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    @Query("select e from EmpleadoEntity e where e.rut = :rut")
    EmpleadoEntity findByRut(@Param("rut") String rut);

    @Query(value = "select * from empleado", nativeQuery = true)
    List<EmpleadoEntity> findAll();
}
