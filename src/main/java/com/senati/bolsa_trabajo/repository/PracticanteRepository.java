package com.senati.bolsa_trabajo.repository;

import com.senati.bolsa_trabajo.model.Practicante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticanteRepository extends JpaRepository<Practicante, Integer> {
}