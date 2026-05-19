package com.example.senatibolsa.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "requerimientos")
@Data
public class Requerimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requerimiento")
    private Integer idRequerimiento;

    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oferta", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OfertaLaboral ofertaLaboral;
    public Requerimiento() {
    }


}