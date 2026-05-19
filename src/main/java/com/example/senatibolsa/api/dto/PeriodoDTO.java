package com.example.senatibolsa.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class PeriodoDTO {
    private int tareasTotales;
    private int tareasCompletadas;
    private int tareasPendientes;
    private int tareasIncompletas;
    private List<ChartDataDTO> chartData;
}
