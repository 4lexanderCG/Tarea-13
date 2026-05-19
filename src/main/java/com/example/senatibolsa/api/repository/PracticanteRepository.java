package com.example.senatibolsa.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.senatibolsa.api.model.Practicante;

@Repository
public interface PracticanteRepository extends JpaRepository<Practicante, Integer>{
    long countByActivoTrue();
    long countByActivoTrueAndPrioridad_IdPrioridad(Integer idPrioridad);
    List<Practicante> findByActivoTrue();
}
