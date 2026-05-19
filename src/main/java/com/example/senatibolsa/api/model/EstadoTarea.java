package com.example.senatibolsa.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados_tarea")
@Data
public class EstadoTarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_tarea")
    private Integer idEstadoTarea;
    @Column(name = "nombre", nullable = false, length = 10, unique = true)
    private String nombre;
}
