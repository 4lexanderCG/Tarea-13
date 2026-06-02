package com.example.senatibolsa.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.senatibolsa.api.model.OfertaLaboral;

@Repository

public interface OfertaLaboralRepositories extends JpaRepository<OfertaLaboral, Integer> {
}