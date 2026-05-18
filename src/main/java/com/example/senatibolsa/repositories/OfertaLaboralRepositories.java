package com.example.senatibolsa.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.senatibolsa.models.OfertaLaboral;

@Repository

public interface OfertaLaboralRepositories extends JpaRepository<OfertaLaboral, Integer> {
}