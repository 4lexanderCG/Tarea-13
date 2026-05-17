package com.example.senatibolsa.api.service;

import com.example.senatibolsa.api.dto.*;
import com.example.senatibolsa.api.model.Practicante;
import com.example.senatibolsa.api.model.Tarea;
import com.example.senatibolsa.api.repository.PracticanteRepository;
import com.example.senatibolsa.api.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private PracticanteRepository practicanteRepo;

    @Autowired
    private TareaRepository tareaRepo;

    public DashboardResponse getDashboardData() {
        DashboardResponse response = new DashboardResponse();
        LocalDate today = LocalDate.now();
        LocalDate inicioSemana = today.with(DayOfWeek.MONDAY);
        LocalDate finSemana = today.with(DayOfWeek.SUNDAY);

        // LÓGICA DE RESUMEN CARD
        ResumenCardDTO resumen = new ResumenCardDTO();
        resumen.setTotalAprendices((int) practicanteRepo.countByActivoTrue());
        
        long totalTareas = tareaRepo.countByActivoTrue();
        long tareasTerminadas = tareaRepo.countByEstadoTarea_IdEstadoTareaAndActivoTrue(1); 
        int promedio = (totalTareas == 0) ? 0 : (int) Math.round((tareasTerminadas * 100.0) / totalTareas);
        resumen.setPromedioGeneral(promedio);
        
        resumen.setEnRiesgo((int) practicanteRepo.countByActivoTrueAndPrioridad_IdPrioridad(1));
        response.setResumenCard(resumen);

        // LÓGICA DE AVANCE (Semana y Mes)
        AvanceDTO avance = new AvanceDTO();
        avance.setSemana(calcularAvance(inicioSemana, finSemana));

        LocalDate inicioMes = today.withDayOfMonth(1);
        LocalDate finMes = today.withDayOfMonth(today.lengthOfMonth());
        avance.setMes(calcularAvance(inicioMes, finMes));

        response.setAvance(avance);

        // LÓGICA DE LISTA DE APRENDICES
        List<Practicante> practicantes = practicanteRepo.findAll();
        List<AprendizDTO> listaAprendices = new ArrayList<>();

        for (Practicante p : practicantes) {
            if (p.getActivo()) {
                AprendizDTO dto = new AprendizDTO();
                dto.setNombreCompleto(p.getNombre() + " " + p.getApellidos());
                dto.setCargo(p.getCargo());
                dto.setArea(p.getArea());
                
                String pri = p.getPrioridad().getNivel();
                dto.setPrioridad(pri.substring(0, 1).toUpperCase() + pri.substring(1).toLowerCase());

                List<Tarea> tareasGenerales = tareaRepo.findByPracticante_IdPracticanteAndActivoTrue(p.getIdPracticante());
                dto.setAvanceGeneral(calcularPorcentaje(tareasGenerales));

                List<Tarea> tareasSemana = tareaRepo.findByPracticante_IdPracticanteAndFechaEntregaBetweenAndActivoTrue(p.getIdPracticante(), inicioSemana, finSemana);
                dto.setAvanceSemanal(calcularPorcentaje(tareasSemana));

                listaAprendices.add(dto);
            }
        }
        response.setListaAprendices(listaAprendices);

        // LÓGICA DE BARRAS DE PROGRESO
        PorcentajesDTO barras = new PorcentajesDTO();
        
        int totSem = avance.getSemana().getTareasTotales();
        barras.setAvanceSemanal(totSem == 0 ? 0 : (int) Math.round((avance.getSemana().getTareasCompletadas() * 100.0) / totSem));
        barras.setIncumplimientoSemanal(totSem == 0 ? 0 : (int) Math.round((avance.getSemana().getTareasIncompletas() * 100.0) / totSem));

        int totMes = avance.getMes().getTareasTotales();
        barras.setAvanceMensual(totMes == 0 ? 0 : (int) Math.round((avance.getMes().getTareasCompletadas() * 100.0) / totMes));
        barras.setIncumplimientoMensual(totMes == 0 ? 0 : (int) Math.round((avance.getMes().getTareasIncompletas() * 100.0) / totMes));

        response.setBarrasProgreso(barras);

        return response;
    }

    private PeriodoDTO calcularAvance(LocalDate inicio, LocalDate fin) {
        List<Tarea> tareasDelPeriodo = tareaRepo.findByFechaEntregaBetweenAndActivoTrue(inicio, fin);
        int completadas = 0, pendientes = 0, incompletas = 0;

        for (Tarea t : tareasDelPeriodo) {
            if (t.getEstadoTarea().getIdEstadoTarea() == 1) completadas++;
            else if (t.getEstadoTarea().getIdEstadoTarea() == 2) pendientes++;
            else if (t.getEstadoTarea().getIdEstadoTarea() == 3) incompletas++;
        }

        PeriodoDTO periodo = new PeriodoDTO();
        periodo.setTareasTotales(tareasDelPeriodo.size());
        periodo.setTareasCompletadas(completadas);
        periodo.setTareasPendientes(pendientes);
        periodo.setTareasIncompletas(incompletas);

        List<ChartDataDTO> chartData = new ArrayList<>();
        chartData.add(new ChartDataDTO("Completada", completadas));
        chartData.add(new ChartDataDTO("Pendiente", pendientes));
        chartData.add(new ChartDataDTO("Incompleta", incompletas));
        periodo.setChartData(chartData);

        return periodo;
    }

    private int calcularPorcentaje(List<Tarea> tareas) {
        if (tareas.isEmpty()) return 0;
        long completadas = tareas.stream().filter(t -> t.getEstadoTarea().getIdEstadoTarea() == 1).count();
        return (int) Math.round((completadas * 100.0) / tareas.size());
    }
}
