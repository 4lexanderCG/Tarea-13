package com.example.senatibolsa.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DashboardResponse {
    @JsonProperty("ResumenCard")
    private ResumenCardDTO resumenCard;
    private AvanceDTO avance;
    private List<AprendizDTO> listaAprendices;
    private PorcentajesDTO barrasProgreso;
}
