package com.example.senatibolsa.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "requerimientos")
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

    public Requerimiento(Integer idRequerimiento, String descripcion, OfertaLaboral ofertaLaboral) {
        this.idRequerimiento = idRequerimiento;
        this.descripcion = descripcion;
        this.ofertaLaboral = ofertaLaboral;
    }

    // Getters y Setters
    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public OfertaLaboral getOfertaLaboral() {
        return ofertaLaboral;
    }

    public void setOfertaLaboral(OfertaLaboral ofertaLaboral) {
        this.ofertaLaboral = ofertaLaboral;
    }

}
