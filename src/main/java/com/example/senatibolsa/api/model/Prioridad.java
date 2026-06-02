package com.example.senatibolsa.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "prioridad")
@Data
public class Prioridad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridad")
    private Integer idPrioridad;
    @Column(name = "nivel", nullable = false, length = 5, unique = true)
    private String nivel;
}
