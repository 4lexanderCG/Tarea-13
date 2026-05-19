package com.example.senatibolsa.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.senatibolsa.api.model.Tarea;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    long countByActivoTrue();

    long countByEstadoTarea_IdEstadoTareaAndActivoTrue(Integer idEstadoTarea);

    List<Tarea> findByFechaEntregaBetweenAndActivoTrue(LocalDate fechaInicio, LocalDate fechaFin);

    List<Tarea> findByPracticante_IdPracticanteAndActivoTrue(Integer idPracticante);

    List<Tarea> findByPracticante_IdPracticanteAndFechaEntregaBetweenAndActivoTrue(Integer idPracticante,
            LocalDate fechaInicio, LocalDate fechaFin);

    List<Tarea> findByActivoTrue();

    @Query("""
                SELECT t.estadoTarea.idEstadoTarea, COUNT(t)
                FROM Tarea t
                WHERE t.fechaEntrega BETWEEN :inicio AND :fin
                  AND t.activo = true
                GROUP BY t.estadoTarea.idEstadoTarea
            """)
    List<Object[]> countTareasPorEstadoEnPeriodo(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

}
