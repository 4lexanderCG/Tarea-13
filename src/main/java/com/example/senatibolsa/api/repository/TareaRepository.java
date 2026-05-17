package com.example.senatibolsa.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.senatibolsa.api.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    long countByActivoTrue();
    long countByEstadoTarea_IdEstadoTareaAndActivoTrue(Integer idEstadoTarea);
    List<Tarea> findByFechaEntregaBetweenAndActivoTrue(LocalDate fechaInicio, LocalDate fechaFin);
    List<Tarea> findByPracticante_IdPracticanteAndActivoTrue(Integer idPracticante);
    List<Tarea> findByPracticante_IdPracticanteAndFechaEntregaBetweenAndActivoTrue(Integer idPracticante, LocalDate fechaInicio, LocalDate fechaFin);
}
