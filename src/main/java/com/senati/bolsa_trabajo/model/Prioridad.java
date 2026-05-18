package com.senati.bolsa_trabajo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prioridad")
@Getter
@Setter
@NoArgsConstructor
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridad")
    private Integer idPrioridad;

    @Column(nullable = false, unique = true, length = 5)
    private String nivel;
}