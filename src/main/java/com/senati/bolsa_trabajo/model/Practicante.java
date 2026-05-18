package com.senati.bolsa_trabajo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "practicantes")
@Getter
@Setter
@NoArgsConstructor
public class Practicante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_practicante")
    private Integer idPracticante;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 80)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_prioridad", nullable = false)
    private Prioridad prioridad;
}