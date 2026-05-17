package com.example.senatibolsa.api.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "practicantes")
@Data
public class Practicante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_practicante")
    private Integer idPracticante;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "apellidos", nullable = false, length = 80)
    private String apellidos;
    @Column(name = "dni", nullable = false, length = 8, unique = true)
    private String dni;
    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;
    @Column(name = "area", nullable = false, length = 50)
    private String area;
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    @ManyToOne
    @JoinColumn(name = "id_prioridad", nullable = false)
    private Prioridad prioridad;
}
