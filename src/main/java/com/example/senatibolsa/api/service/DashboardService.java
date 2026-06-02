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
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Practicante> practicantesActivos = practicanteRepo.findByActivoTrue();

        List<Tarea> todasLasTareas = tareaRepo.findByActivoTrue();
        Map<Integer, List<Tarea>> tareasPorPracticante = todasLasTareas.stream()
                .collect(Collectors.groupingBy(t -> t.getPracticante().getIdPracticante()));

        List<Tarea> tareasSemana = tareaRepo.findByFechaEntregaBetweenAndActivoTrue(inicioSemana, finSemana);
        Map<Integer, List<Tarea>> tareasSemanaPorPracticante = tareasSemana.stream()
                .collect(Collectors.groupingBy(t -> t.getPracticante().getIdPracticante()));

        List<AprendizDTO> listaAprendices = new ArrayList<>();

        for (Practicante p : practicantesActivos) {
            AprendizDTO dto = new AprendizDTO();
            dto.setNombreCompleto(p.getNombre() + " " + p.getApellidos());
            dto.setCargo(p.getCargo());
            dto.setArea(p.getArea());

            String pri = p.getPrioridad().getNivel();
            dto.setPrioridad(pri.substring(0, 1).toUpperCase() + pri.substring(1).toLowerCase());

            List<Tarea> tGenerales = tareasPorPracticante.getOrDefault(p.getIdPracticante(), new ArrayList<>());
            dto.setAvanceGeneral(calcularPorcentaje(tGenerales));

            List<Tarea> tSemana = tareasSemanaPorPracticante.getOrDefault(p.getIdPracticante(), new ArrayList<>());
            dto.setAvanceSemanal(calcularPorcentaje(tSemana));

            listaAprendices.add(dto);
        }
        response.setListaAprendices(listaAprendices);

        // LÓGICA DE BARRAS DE PROGRESO
        PorcentajesDTO barras = new PorcentajesDTO();

        int totSem = avance.getSemana().getTareasTotales();
        barras.setAvanceSemanal(
                totSem == 0 ? 0 : (int) Math.round((avance.getSemana().getTareasCompletadas() * 100.0) / totSem));
        barras.setIncumplimientoSemanal(
                totSem == 0 ? 0 : (int) Math.round((avance.getSemana().getTareasIncompletas() * 100.0) / totSem));

        int totMes = avance.getMes().getTareasTotales();
        barras.setAvanceMensual(
                totMes == 0 ? 0 : (int) Math.round((avance.getMes().getTareasCompletadas() * 100.0) / totMes));
        barras.setIncumplimientoMensual(
                totMes == 0 ? 0 : (int) Math.round((avance.getMes().getTareasIncompletas() * 100.0) / totMes));

        response.setBarrasProgreso(barras);

        return response;
    }

    private PeriodoDTO calcularAvance(LocalDate inicio, LocalDate fin) {
        List<Object[]> resultados = tareaRepo.countTareasPorEstadoEnPeriodo(inicio, fin);
        int completadas = 0, pendientes = 0, incompletas = 0;
        int totalTareas = 0;

        for (Object[] fila : resultados) {
            Integer idEstado = (Integer) fila[0];
            Long count = (Long) fila[1];

            if (idEstado == 1)
                completadas = count.intValue();
            else if (idEstado == 2)
                pendientes = count.intValue();
            else if (idEstado == 3)
                incompletas = count.intValue();

            totalTareas += count.intValue();
        }

        PeriodoDTO periodo = new PeriodoDTO();
        periodo.setTareasTotales(totalTareas);
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
        if (tareas.isEmpty())
            return 0;
        long completadas = tareas.stream().filter(t -> t.getEstadoTarea().getIdEstadoTarea() == 1).count();
        return (int) Math.round((completadas * 100.0) / tareas.size());
    }
}
