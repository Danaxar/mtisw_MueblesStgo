package com.mtisw1.mueblesStgo.repositories;
import com.mtisw1.mueblesStgo.entities.AutorizacionEntity;
import com.mtisw1.mueblesStgo.entities.JustificativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorizacionRepository extends JpaRepository<AutorizacionEntity, Long> {

}
