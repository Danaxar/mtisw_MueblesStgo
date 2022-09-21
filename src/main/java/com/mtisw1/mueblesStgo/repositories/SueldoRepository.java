package com.mtisw1.mueblesStgo.repositories;

import com.mtisw1.mueblesStgo.entities.Sueldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SueldoRepository extends JpaRepository<Sueldo, Long> {
}
