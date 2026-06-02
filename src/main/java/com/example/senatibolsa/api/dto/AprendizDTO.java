package com.example.senatibolsa.api.dto;

import lombok.Data;

@Data
public class AprendizDTO {
    private String nombreCompleto;
    private String cargo;
    private String area;
    private String prioridad;
    private int avanceGeneral;
    private int avanceSemanal;
}
