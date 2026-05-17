package com.example.senatibolsa.api.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tareas")
@Data
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Integer idTarea;
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    @ManyToOne
    @JoinColumn(name = "id_practicante", nullable = false)
    private Practicante practicante;
    @ManyToOne
    @JoinColumn(name = "id_estado_tarea", nullable = false)
    private EstadoTarea estadoTarea;    
}
