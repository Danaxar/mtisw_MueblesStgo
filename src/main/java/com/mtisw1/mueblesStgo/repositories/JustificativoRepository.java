package com.mtisw1.mueblesStgo.repositories;
import com.mtisw1.mueblesStgo.entities.JustificativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JustificativoRepository extends JpaRepository<JustificativoEntity, Long> {

}
